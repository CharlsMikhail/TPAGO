package com.example.tpago2.gui.aperturarCuenta

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tpago2.R

class IngresarSesionFragment : Fragment(R.layout.fragment_ingresar_sesion) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnIniciar = view.findViewById<Button>(R.id.btn_iniciar)
        btnIniciar.setOnClickListener{
            view.findNavController().navigate(R.id.action_ingresarSesionFragment_to_menuFragment)
        }
    }
}