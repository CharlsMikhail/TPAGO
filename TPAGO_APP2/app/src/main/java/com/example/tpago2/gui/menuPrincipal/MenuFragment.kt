package com.example.tpago2.gui.menuPrincipal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario

class MenuFragment : Fragment(R.layout.fragment_menu) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actualizarInterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnPagar = view.findViewById<LinearLayout>(R.id.ll_pagar)
        btnPagar.setOnClickListener{
            view.findNavController().navigate(R.id.action_menuFragment_to_listarContactosFragment)
        }
    }

    private fun actualizarInterfaz(view: View) {
        val txtNameUser = view.findViewById<TextView>(R.id.txt_user_name)
    }

}