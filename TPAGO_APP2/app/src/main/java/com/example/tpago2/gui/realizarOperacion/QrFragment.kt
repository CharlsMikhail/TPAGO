package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.CuentaDestino
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO
import kotlin.random.Random
import com.google.android.material.floatingactionbutton.FloatingActionButton

class QrFragment : Fragment(R.layout.fragment_qr) {

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
        }

        eventos(view)

    }

    private fun eventos(view: View) {
        val btnQR = view.findViewById<ImageButton>(R.id.qr_button)
        val daoCueUsu = CuentaUsuarioDAO(requireContext())
        val listaUsuariosDestino = daoCueUsu.obtenerTodasLasCuentasUsuarioExcepto(cuentaActual.num_movil)

        btnQR.setOnClickListener {
            val cuentaRamdom = listaUsuariosDestino.random()
            val cuentaDestino = daoCueUsu.obtenerUsuarioDestinoPorNumMovil(cuentaRamdom.num_movil)

            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            delivery.putSerializable(KEY_USUARIO_DESTINO, cuentaDestino)
            view.findNavController().navigate(R.id.action_qrFragment_to_pagarFragment, delivery)
        }
    }
}