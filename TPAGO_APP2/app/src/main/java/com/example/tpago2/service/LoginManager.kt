package com.example.tpago2.service

import android.content.Context
import android.content.SharedPreferences

class LoginManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "MyPrefs", Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun guardarNumMovil(numMovil: Int) {
        editor.putInt("numMovil", numMovil)
        editor.apply()
    }

    fun obtenerNumMovil(): Int {
        return sharedPreferences.getInt("numMovil", 0) // 0 es el valor por defecto si no se encuentra el dato
    }

    fun guardarDniPersona(dniPersona: Int) {
        editor.putInt("dniPersona", dniPersona)
        editor.apply()
    }

    fun obtenerDniPersona(): Int {
        return sharedPreferences.getInt("dniPersona", 0)
    }

}