package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.tpago2.R

class PagarFragment : Fragment(R.layout.fragment_pagar) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val x = view.findViewById<TextView>(R.id.txt_nombre_usuario)

        x.setOnClickListener{
            Toast.makeText(context, "hola amigos", Toast.LENGTH_LONG).show()
        }

    }
}