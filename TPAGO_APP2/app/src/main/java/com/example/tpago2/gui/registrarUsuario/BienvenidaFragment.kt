package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import com.example.tpago2.R

class BienvenidaFragment : Fragment(R.layout.fragment_bienvenida) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actualizarInterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnIniciarSesion = view.findViewById<Button>(R.id.btnIniciar)
        val btnRegistrarse = view.findViewById<Button>(R.id.btnRegistrar)

        btnIniciarSesion.setOnClickListener {
            view.findNavController().navigate(R.id.action_bienvenidaFragment_to_iniciarSesionFragment)
        }

        btnRegistrarse.setOnClickListener {
            view.findNavController().navigate(R.id.action_bienvenidaFragment_to_registrarUsuarioFragment)
        }


    }

    private fun actualizarInterfaz(view: View) {

    }

}