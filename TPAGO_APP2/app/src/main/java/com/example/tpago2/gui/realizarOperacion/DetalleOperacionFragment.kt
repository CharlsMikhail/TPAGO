package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import com.example.tpago2.R

class DetalleOperacionFragment : Fragment(R.layout.fragment_detalle_operacion) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnInicio = view.findViewById<Button>(R.id.btn_inicio)

        btnInicio.setOnClickListener {
            view.findNavController().navigate(R.id.menuFragment)
        }

    }
}