package com.example.tpago2.gui.menuPrincipal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tpago2.R
import com.example.tpago2.bd.CargaMasiva

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cargarBD = CargaMasiva(this)
    }
}