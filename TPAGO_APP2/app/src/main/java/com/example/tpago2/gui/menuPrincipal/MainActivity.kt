package com.example.tpago2.gui.menuPrincipal

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tpago2.R
import com.example.tpago2.bd.CargaMasiva

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verificar si la carga masiva ya se realizó previamente
        val prefs: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun: Boolean = prefs.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // Realizar la carga masiva
            val cargaMasiva = CargaMasiva(this)
            cargaMasiva.realizarCargaMasiva()

            // Marcar que la carga masiva ya se ha realizado
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()
        }

    }
}
