package com.example.tpago2.bd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tpago2.R
import com.example.tpago2.data.dao.PersonaDAO

class TestFragment : Fragment(R.layout.fragment_test) {
    private lateinit var personaDao: PersonaDAO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carga = CargaMasiva(view.context)
        val dbHelper = DataBaseHelper.getInstance(view.context)
        personaDao = PersonaDAO(view.context)

        // Cargar datos masivos (comentar esta línea después de la primera ejecución)
        // carga.realizarCargaMasiva()

        val btnConsultar = view.findViewById<Button>(R.id.btn_consultar)
        val txtConsulta = view.findViewById<TextView>(R.id.txt_consulta)

        btnConsultar.setOnClickListener {
            val personas = personaDao.obtenerTodasLasPersonas()
            var acum = ""
            personas.forEach { persona ->
                acum += "DNI: ${persona.dni_persona}, Nombre: ${persona.primer_nombre}\n"
            }
            txtConsulta.text = acum
        }
    }
}
