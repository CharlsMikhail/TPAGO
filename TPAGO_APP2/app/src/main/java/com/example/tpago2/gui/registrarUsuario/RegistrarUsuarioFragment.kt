package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.PersonaDAO
import com.example.tpago2.data.dao.UsuarioDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.ReniecProvider
import java.sql.Date
import java.time.LocalDateTime

class RegistrarUsuarioFragment : Fragment(R.layout.fragment_registrar_usuario) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actualizarInterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnRegistrar = view.findViewById<Button>(R.id.buttonLogin)
        val etDNI = view.findViewById<EditText>(R.id.EtxDNI)
        val etCelular = view.findViewById<EditText>(R.id.etxTelefono)
        val etKey = view.findViewById<EditText>(R.id.etxPassword)
        val etEmail = view.findViewById<EditText>(R.id.etxcorreo)

        btnRegistrar.setOnClickListener {
            val dni = etDNI.text.toString()
            val celular = etCelular.text.toString()
            val key = etKey.text.toString()
            val email = etEmail.text.toString()

            // Validaciones y/o restricciones para estas entradas -> FORMA.

            if (dni.length != 8 || dni.isEmpty()) {
                etDNI.error = "DNI debe contener 8 dígitos."
            }

            // Testear
            if (!isValidPhoneNumber(celular) || celular.isEmpty()) {
                etCelular.error = "Celular debe contener 9 dígitos."
            }

            if (key.length < 8 || key.isEmpty()) {
                etKey.error = "Contraseña debe contener al menos 8 dígitos."
            }

            if (email.isEmpty()) { // El correo electronico se puede repetir.
                etEmail.error = "Correo electrónico es obligatorio."
            }
            if (!isValidEmail(email) || email.isEmpty()) {
                etEmail.error = "Formato de correo electrónico inválido."
            }

            // Validación en cuestion de FONDO.

            // El dni tiene que existir en la base de datos de RENIEC.
            val persona = ReniecProvider.getPersonaByDni(dni.toInt())
            if (persona != null){
                // Insertamos a la persona en la base de datos, si es que no existe.
                val daoPersona = PersonaDAO(requireContext())
                val daoUsuario = UsuarioDAO(requireContext())
                val currentDateTime = LocalDateTime.now()
                if (daoPersona.obtenerPersonaPorDni(dni.toInt()) == null) {
                    // Insertamos el usuario en la base de datos.
                    daoPersona.insertarPersona(persona.dni_persona, persona.primer_nombre, persona.segundo_nombre, persona.ape_paterno, persona.ape_materno)
                    daoUsuario.insertarUsuario(persona.dni_persona, currentDateTime.toLocalDate().toString(), "Usuario Activo")
                    // Insertamos a la cuenta usuario en la base de datos --> Posteriormente a la validacion del DNI.
                }

                val cuentaRegistrarUsuario = CuentaUsuario(celular.toInt(), dni.toInt(), 0, "192.168.10.23", key.toInt(), 500, email, 1)
                val delivery = Bundle()
                delivery.putSerializable(KEY_PERSONA, persona) // Cuidado
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaRegistrarUsuario)
                view.findNavController().navigate(R.id.action_registrarUsuarioFragment_to_iniciarValidarDniFragment, delivery)

            } else {
                etDNI.error = "DNI no pertence a la RENIEC."
            }
        }
    }


    private fun actualizarInterfaz(view: View) {

    }

    fun isValidEmail(email: String): Boolean {
        // Expresión regular para validar el formato del correo electrónico
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Este es un ejemplo simple, se puede mejorar según las necesidades
        val phoneNumberPattern = "^\\d{9}$"
        return phoneNumber.matches(phoneNumberPattern.toRegex())
    }

}