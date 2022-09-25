package com.example.week1
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.week1.database.globalvar
import com.example.week1.databinding.ActivityResultactivityBinding
import com.example.week1.model.Hewan
import com.google.android.material.snackbar.Snackbar

class Resultactivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityResultactivityBinding
    private var pcc: Int = -1
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            viewBind.textView1.setImageURI(uri)
            globalvar.listDataAnimal[pcc].imageUri = uri.toString()
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
        val Hewan = globalvar.listDataAnimal[pcc]
        super.onResume()

        show(Hewan)
    }
    private fun getintent(){
        pcc = intent.getIntExtra("position", -1)
        val Hewan = globalvar.listDataAnimal[pcc]
//        if(Hewan.imageUri!!.isEmpty())
//        {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                baseContext.contentResolver.takePersistableUriPermission(Uri.parse(globalvar.listDataAnimal[pcc].imageUri),
////                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
////                )
//            }
//        }
        show(Hewan)
    }

    private fun show(hewan: Hewan){
        viewBind.textView1.setImageURI(Uri.parse(globalvar.listDataAnimal[pcc].imageUri))
        viewBind.namahewan.text = hewan.nama
        viewBind.usiaHewan.text = hewan.usia.toString()
    }
    private fun delete(){

    }

    private fun listener(){

        viewBind.Delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Hewan")
            builder.setMessage("Are you sure you want to delete this Hewan?")


            builder.setPositiveButton(android.R.string.yes) { function, which ->
                val snackbar = Snackbar.make(viewBind.Delete, "Hewan Deleted", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Dismiss") { snackbar.dismiss() }
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setBackgroundTint(Color.GRAY)
                snackbar.show()

                //remove
                globalvar.listDataAnimal.removeAt(pcc)
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