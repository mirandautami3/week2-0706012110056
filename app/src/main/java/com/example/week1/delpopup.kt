package com.example.week1

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.week1.databinding.ActivityPopupdeleteBinding

class delpopup : AppCompatActivity() {
    private lateinit var viewBind: ActivityPopupdeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityPopupdeleteBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        supportActionBar?.hide()
        var wdh:Int = displayMetrics.widthPixels
        var hght:Int = displayMetrics.heightPixels

        window.setLayout((wdh*.7).toInt(), (hght*0.4).toInt())
    }
}