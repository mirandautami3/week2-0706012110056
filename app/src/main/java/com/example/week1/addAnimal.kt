package com.example.week1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.week1.database.Globalvar
import com.example.week1.databinding.ActivityAddBinding
import com.example.week1.model.animal


class addAnimal : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddBinding
    private lateinit var data: animal
    var pcc = -1
    var img: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getintent()
        listener()
    }
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val uri = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(uri != null){
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }}
            viewBind.imageView2.setImageURI(uri)
            img = uri.toString()
        }
    }

    private fun getintent(){
        pcc = intent.getIntExtra("position", -1)
        if(pcc != -1){
            val movie = Globalvar.listDataAnimal[pcc]
            viewBind.toolbar2.title = "Edit movie"
            viewBind.Addmovie.text = "Edit"
            viewBind.imageView2.setImageURI(Uri.parse(Globalvar.listDataAnimal[pcc].imageUri))
            viewBind.Rating.editText?.setText(movie.rating.toString())
            viewBind.Title.editText?.setText(movie.title)
            viewBind.Genre.editText?.setText(movie.genre)
            viewBind.company.editText?.setText(movie.company)
            viewBind.description.editText?.setText(movie.synopsis)
        }
    }

    private fun listener(){
        viewBind.imageView2.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.Addmovie.setOnClickListener{
            var title = viewBind.Title.editText?.text.toString().trim()
            var rating = 0
            var genre = viewBind.Genre.editText?.text.toString().trim()
            var company = viewBind.company.editText?.text.toString().trim()
            var description = viewBind.description.editText?.text.toString().trim()

            data = animal(title, rating, genre, company, description)
            checker()
        }

        viewBind.toolbar2.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker()
    {
        var isCompleted:Boolean = true

        if(data.title!!.isEmpty()){
            viewBind.Title.error = "Title cannot be empty"
            isCompleted = false
        }else{
            viewBind.Title.error = ""
        }

        if(data.genre!!.isEmpty()){
            viewBind.Genre.error = "genre cannot be empty"
            isCompleted = false
        }else{
            viewBind.Genre.error = ""
        }

        if(data.company!!.isEmpty()){
            viewBind.company.error = "company cannot be empty"
            isCompleted = false
        }else{
            viewBind.company.error = ""
        }

        if(data.synopsis!!.isEmpty()){
            viewBind.description.error = "synopsis cannot be empty"
            isCompleted = false
        }else{
            viewBind.description.error = ""
        }

        data.imageUri = img.toString()


        if(viewBind.Rating.editText?.text.toString().isEmpty() || viewBind.Rating.editText?.text.toString().toInt() < 0)
        {
            viewBind.Rating.error = "rating cannot be empty or 0"
            isCompleted = false
        }

        if(isCompleted == true)
        {
            if(pcc == -1)
            {
                data.rating = viewBind.Rating.editText?.text.toString().toInt()
                Globalvar.listDataAnimal.add(data)

            }else
            {
                data.rating = viewBind.Rating.editText?.text.toString().toInt()
                Globalvar.listDataAnimal[pcc] = data
            }
            finish()
        }
    }
}