package com.example.week1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.week1.database.globalvar
import com.example.week1.databinding.ActivityAddBinding
import com.example.week1.model.Hewan
import kotlinx.android.synthetic.main.activity_add.*

class addAnimal : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddBinding
    private lateinit var data: Hewan
    var pcc = -1
    var img: String = ""
    var radioGroup = radio_group
    var radioButton = radio1_ayam
    var button = Addhewan

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
            val Hewan = globalvar.listDataAnimal[pcc]
            viewBind.toolbar2.title = "Edit hewan"
            viewBind.Addhewan.text = "Edit"
            viewBind.imageView2.setImageURI(Uri.parse(globalvar.listDataAnimal[pcc].imageUri))
            viewBind.Nama.editText?.setText(Hewan.nama)
            viewBind.Usia.editText?.setText(Hewan.usia.toString())
        }
    }

    private fun listener(){
        viewBind.imageView2.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.Addhewan.setOnClickListener{
            var nama = viewBind.Nama.editText?.text.toString().trim()
            var usia = 0
            var imageUri = "sr"

            data = Hewan(nama, usia, imageUri)
            checker()
        }

        viewBind.toolbar2.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker()
    {
        var isCompleted:Boolean = true

        if(data.nama!!.isEmpty()){
            viewBind.Nama.error = "Title cannot be empty"
            isCompleted = false
        }else{
            viewBind.Nama.error = ""
        }


        data.imageUri = img.toString()


        if(viewBind.Usia.editText?.text.toString().isEmpty() || viewBind.Usia.editText?.text.toString().toInt() < 0)
        {
            viewBind.Usia.error = "rating cannot be empty or 0"
            isCompleted = false
        }

        if(isCompleted == true)
        {
            if(pcc == -1)
            {
                data.usia = viewBind.Usia.editText?.text.toString().toInt()
                globalvar.listDataAnimal.add(data)

            }else
            {
                data.usia = viewBind.Usia.editText?.text.toString().toInt()
                globalvar.listDataAnimal[pcc] = data
            }
            finish()
        }
    }
}