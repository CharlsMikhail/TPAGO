package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.validarDNI

class DniValidadoFragment : Fragment(R.layout.fragment_dni_validado) {

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
        val delivery = Bundle()
        delivery.putSerializable(KEY_PERSONA, personaRegistroActual) // Cuidado
        delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaRegistroActual)
        btnContinuar.setOnClickListener {
            if (!validarDNI) {
                validarDNI = true
                view.findNavController().navigate(R.id.validarDniFragment, delivery)
            } else {
                validarDNI = false
                //Aqui ya insertamos la cuenta de ususario a la BD.

                val daoCuentaUsuario = CuentaUsuarioDAO(requireContext())
                daoCuentaUsuario.insertarCuentaUsuario(cuentaRegistroActual.num_movil, personaRegistroActual.dni_persona, cuentaRegistroActual.saldo, cuentaRegistroActual.ip_cuenta_usuario, cuentaRegistroActual.contrasenia, cuentaRegistroActual.limite_por_transaccion, cuentaRegistroActual.email, cuentaRegistroActual.estado_de_actividad)

                view.findNavController().navigate(R.id.action_dniValidadoFragment_to_bienvenidaTPAGOFragment, delivery)
            }
        }
    }

    private fun actualizarInterfaz(view: View) {

    }

}