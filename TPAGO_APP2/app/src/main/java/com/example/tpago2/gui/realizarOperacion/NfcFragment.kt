package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO

class NfcFragment : Fragment(R.layout.fragment_nfc) {
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
        val btnNFC = view.findViewById<ImageButton>(R.id.nfc_imgbtn)
        val daoCueUsu = CuentaUsuarioDAO(requireContext())
        val listaUsuariosDestino = daoCueUsu.obtenerTodasLasCuentasUsuarioExcepto(cuentaActual.num_movil)

        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras_nfc)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }


        btnNFC.setOnClickListener {
            val cuentaRamdom = listaUsuariosDestino.random()
            val cuentaDestino = daoCueUsu.obtenerUsuarioDestinoPorNumMovil(cuentaRamdom.num_movil)

            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            delivery.putSerializable(KEY_USUARIO_DESTINO, cuentaDestino)
            view.findNavController().navigate(R.id.action_nfcFragment_to_pagarFragment, delivery)
        }
    }


}