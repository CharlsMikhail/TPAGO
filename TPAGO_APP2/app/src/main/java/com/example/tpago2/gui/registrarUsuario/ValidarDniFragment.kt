package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.validarDNI

class ValidarDniFragment : Fragment(R.layout.fragment_validar_dni) {

    private lateinit var personaRegistroActual: Persona
    private lateinit var cuentaRegistroActual: CuentaUsuario

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            personaRegistroActual = it.getSerializable(KEY_PERSONA) as Persona
            cuentaRegistroActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
        }

        actualizarInterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {

        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras_nfc)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        val btnContinuar = view.findViewById<ImageButton>(R.id.imagefoto)
        val delivery = Bundle()
        delivery.putSerializable(KEY_PERSONA, personaRegistroActual) // Cuidado
        delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaRegistroActual)

        btnContinuar.setOnClickListener {
            view.findNavController().navigate(R.id.action_validarDniFragment_to_dniValidadoFragment, delivery)
        }
    }

    private fun actualizarInterfaz(view: View) {
        if (validarDNI) {
            val imgDNI = view.findViewById<ImageView>(R.id.dni_imgbtn)
            imgDNI.setImageResource(R.drawable.reverse)
            val txtDNI = view.findViewById<TextView>(R.id.txt_dniconfi)
            txtDNI.text = getString(R.string.dni_atras)
        }
    }

}