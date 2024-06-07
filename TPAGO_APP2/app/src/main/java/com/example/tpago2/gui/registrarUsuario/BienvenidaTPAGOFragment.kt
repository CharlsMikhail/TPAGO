package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.LoginManager

class BienvenidaTPAGOFragment : Fragment(R.layout.fragment_bienvenida_t_p_a_g_o) {
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
        val btnContinuar = view.findViewById<TextView>(R.id.button)
        btnContinuar.setOnClickListener {
            // Guardar información en SharedPreferences
            val lm = LoginManager(requireContext())

            lm.guardarNumMovil(cuentaRegistroActual.num_movil)
            lm.guardarDniPersona(personaRegistroActual.dni_persona)
            view.findNavController().popBackStack(R.id.menuFragment, false)
        }
    }

    private fun actualizarInterfaz(view: View) {
        val txtNombre = view.findViewById<TextView>(R.id.nombre)
        txtNombre.text = personaRegistroActual.primer_nombre
    }

}