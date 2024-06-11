package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.UsuarioDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.validarDNI

class IniciarValidarDniFragment : Fragment(R.layout.fragment_iniciar_validar_dni) {

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
        val btnContinuar = view.findViewById<Button>(R.id.buttonLogin)

        btnContinuar.setOnClickListener {
            val delivery = Bundle()
            delivery.putSerializable(KEY_PERSONA, personaRegistroActual) // Cuidado
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaRegistroActual)
            view.findNavController().navigate(R.id.action_iniciarValidarDniFragment_to_validarDniFragment, delivery)
        }

    }

    private fun actualizarInterfaz(view: View) {

    }

}