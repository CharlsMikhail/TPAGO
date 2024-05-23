package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R

class PagarFragment : Fragment(R.layout.fragment_pagar) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnPagar = view.findViewById<Button>(R.id.btn_pagar)

        btnPagar.setOnClickListener{
            view.findNavController().navigate(R.id.action_pagarFragment_to_detalleOperacionFragment)
        }
    }
}