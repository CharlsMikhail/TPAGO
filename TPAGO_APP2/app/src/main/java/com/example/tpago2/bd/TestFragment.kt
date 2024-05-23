package com.example.tpago2.bd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tpago2.R

class TestFragment : Fragment(R.layout.fragment_test) {
    private lateinit var dbHelper: DataBaseHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carga = CargaMasiva(view.context)

        dbHelper = carga.realizarCargaMasiva()

        // Para consultar en la BD
        val db = dbHelper.writableDatabase

        val btnConectar = view.findViewById<Button>(R.id.btn_consultar)
        val txtConsulta = view.findViewById<TextView>(R.id.txt_consulta)

        var acum = ""
        btnConectar.setOnClickListener {
            val cursor = db.rawQuery("SELECT * FROM persona", null)
            if (cursor.moveToFirst()) {
                do {
                    val dniPersona = cursor.getInt(cursor.getColumnIndexOrThrow("dni_persona"))
                    val primerNombre = cursor.getString(cursor.getColumnIndexOrThrow("primer_nombre"))
                    acum += "DNI: $dniPersona, Nombre: $primerNombre"
                } while (cursor.moveToNext())
            }
            cursor.close()
            txtConsulta.text = acum
        }

    }
}