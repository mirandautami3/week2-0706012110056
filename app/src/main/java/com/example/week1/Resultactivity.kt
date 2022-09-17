package com.example.week1
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.week1.database.Globalvar
import com.example.week1.databinding.ActivityResultactivityBinding
import com.example.week1.model.animal
import com.google.android.material.snackbar.Snackbar

class Resultactivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityResultactivityBinding
    private var pcc: Int = -1
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            viewBind.imageView.setImageURI(uri)
            Globalvar.listDataAnimal[pcc].imageUri = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityResultactivityBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getintent()
        listener()
    }

    override fun onResume() {
        val movie = Globalvar.listDataAnimal[pcc]
        super.onResume()

        show(movie)
    }
    private fun getintent(){
        pcc = intent.getIntExtra("position", -1)
        val movie = Globalvar.listDataAnimal[pcc]
        if(!movie.imageUri.isEmpty())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.getContentResolver().takePersistableUriPermission(Uri.parse(Globalvar.listDataAnimal[pcc].imageUri),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }
        show(movie)
    }

    private fun show(movie: animal){
        viewBind.imageView.setImageURI(Uri.parse(Globalvar.listDataAnimal[pcc].imageUri))
        viewBind.jenis2.text = movie.genre
        viewBind.judul.text = movie.title
        viewBind.rating.text = movie.rating.toString()
        viewBind.textView2.text = movie.company
        viewBind.des.text = movie.synopsis
    }
    private fun delete(){

    }

    private fun listener(){

        viewBind.Delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete movie")
            builder.setMessage("Are you sure you want to delete this movie?")


            builder.setPositiveButton(android.R.string.yes) { function, which ->
                val snackbar = Snackbar.make(viewBind.Delete, "Movie Deleted", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Dismiss") { snackbar.dismiss() }
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setBackgroundTint(Color.GRAY)
                snackbar.show()

                //remove
                Globalvar.listDataAnimal.removeAt(pcc)
                finish()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        viewBind.toolbar.getChildAt(1).setOnClickListener {
            finish()
        }

        viewBind.floatingActionButton.setOnClickListener {
            val myintent = Intent(this, addAnimal::class.java).apply {
                putExtra("position", pcc)
            }
            startActivity(myintent)
        }
    }
}