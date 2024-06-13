package com.example.tpago2.gui.registrarUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
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
                return@setOnClickListener
            }

            if (email.isEmpty()) { // El correo electronico se puede repetir.
                etEmail.error = "Correo electrónico es obligatorio."
                return@setOnClickListener
            }

            if (hasSpaceBetweenCharacters(email)) {
                etEmail.error = "No puede contener espacios."
                return@setOnClickListener
            }


            if (!hasValidLength(email)) {
                etEmail.error = "El nombre de usuario debe contener entre 6 y 30 caracteres y tambien '@'."
                return@setOnClickListener
            }

            if (!hasValidOrganization(email)) {
                etEmail.error = "La organización solo puede ser: gmail, yahoo, outlook, hotmail o ulasalle.\n (no puede estar vacio)"
                return@setOnClickListener
            }

            if (!hasValidDomainType(email)) {
                etEmail.error = "El dominio solo puede ser: .com, .net o .org.\n (no puede estar vacio)"
                return@setOnClickListener
            }

            // Testear
            if (!isValidPhoneNumber(celular) || celular.isEmpty()) {
                etCelular.error = "Celular debe contener 9 dígitos y comenzar con el '9'."
                return@setOnClickListener
            }

            if (key.length < 6 || key.isEmpty()) {
                etKey.error = "Contraseña debe contener al menos 8 dígitos."
                return@setOnClickListener
            }


            // Validación en cuestion de FONDO del DNI.

            // El dni tiene que existir en la base de datos de RENIEC.
            val persona = ReniecProvider.getPersonaByDni(dni.toInt())
            if (persona != null){

                // Validación en cuestion de FONDO de celular.
                val daoCuentaUsuario = CuentaUsuarioDAO(requireContext())
                if (daoCuentaUsuario.obtenerCuentaUsuarioPorNumMovil(celular.toInt()) != null) {
                    etCelular.error = "Celular ya registrado."
                    return@setOnClickListener
                }



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
                return@setOnClickListener
            }

        }
    }


    private fun actualizarInterfaz(view: View) {

    }

    private fun hasSpaceBetweenCharacters(input: String): Boolean {
        val spaceRegex = ".*\\s+.*"
        return input.matches(spaceRegex.toRegex())
    }


    private fun hasValidLength(email: String): Boolean {
        val usernameRegex = "[A-Za-z0-9+_.-]{6,30}"
        return email.matches("$usernameRegex@.*".toRegex())
    }

    private fun hasValidOrganization(email: String): Boolean {
        val organizationRegex = "(gmail|yahoo|outlook|hotmail|ulasalle)"
        return email.matches(".*$organizationRegex.*".toRegex())
    }

    private fun hasValidDomainType(email: String): Boolean {
        val domainTypeRegex = "(.com|.net|.org)"
        return email.matches(".*$domainTypeRegex".toRegex())
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberPattern = "^9\\d{8}$"
        return phoneNumber.matches(phoneNumberPattern.toRegex())
    }

}