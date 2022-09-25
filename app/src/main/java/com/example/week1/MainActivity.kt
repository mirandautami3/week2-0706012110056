package com.example.week1


import Interface.cardListener
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week1.Adapter.adapter
import com.example.week1.database.globalvar
import com.example.week1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), cardListener {
    private lateinit var viewBind:ActivityMainBinding
    private var tot: Int = 0
    private val adapter = adapter(globalvar.listDataAnimal, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        setupRecyclerView()
        listener()
    }

    override fun onResume() {
        super.onResume()
        tot = globalvar.listDataAnimal.size
        if(tot == 0)
        {
            viewBind.usiaHewan.alpha = 1f
        }else
        {
            viewBind.usiaHewan.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == globalvar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, Resultactivity::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myIntent)
    }

    private fun listener(){
        viewBind.button.setOnClickListener {
            val myIntent = Intent(this, addAnimal::class.java)
            startActivity(myIntent)
        }
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(this,1)
        viewBind.listdata.layoutManager = layoutManager   // Set layout
        viewBind.listdata.adapter = adapter   // Set adapter
    }
}