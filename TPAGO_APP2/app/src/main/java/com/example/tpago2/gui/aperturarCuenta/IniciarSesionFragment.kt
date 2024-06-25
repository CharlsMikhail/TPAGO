package com.example.tpago2.gui.aperturarCuenta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.PersonaDAO
import com.example.tpago2.data.dao.UsuarioDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.gui.utilitarios.showCustomSnackBar
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.LoginManager

class IniciarSesionFragment : Fragment(R.layout.fragment_iniciar_sesion) {

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)
    }

    private fun eventos(view: View) {
        val editDNI = view.findViewById<EditText>(R.id.editTextDNI)
        val editMOVIL = view.findViewById<EditText>(R.id.editTextPhone)
        val editKEY = view.findViewById<EditText>(R.id.editTextPassword)

        val btnLog = view.findViewById<Button>(R.id.buttonLogin)

        btnLog.setOnClickListener {

            if (editDNI.text.isEmpty() or editMOVIL.text.isEmpty() or editKEY.text.isEmpty()) {
                //Toast.makeText(requireContext(), "Datos incompletos, intente de nuevo", Toast.LENGTH_SHORT).show()
                showCustomSnackBar(requireView(),  "¡Atención!", "Datos incompletos, intente de nuevo")

                return@setOnClickListener
            }

            val dni = editDNI.text.toString().toInt()
            val movil = editMOVIL.text.toString().toInt()
            val key = editKEY.text.toString().toInt()

            // Validar datos aquí utilizando los DAOs

            val personaDAO = PersonaDAO(requireContext())
            val usuarioDAO = UsuarioDAO(requireContext())
            val cuentaUsuarioDAO = CuentaUsuarioDAO(requireContext())


            // Suponiendo que el usuario se valida por DNI y móvil
            val persona = personaDAO.obtenerPersonaPorDni(dni)
            val usuario = usuarioDAO.obtenerUsuarioPorDni(dni)
            val cuentaUsuario = cuentaUsuarioDAO.obtenerCuentaUsuarioPorNumMovil(movil)


            if (usuario != null && cuentaUsuario != null && persona != null && persona.dni_persona == cuentaUsuario.dni_persona && key == cuentaUsuario.contrasenia) {
                // Usuario y cuenta validados correctamente
                personaActual = persona
                usuarioActual = usuario
                cuentaActual = cuentaUsuario

                // Guardar información en SharedPreferences
                val lm = LoginManager(requireContext())

                lm.guardarNumMovil(cuentaActual.num_movil)
                lm.guardarDniPersona(personaActual.dni_persona)

                val delivery = Bundle()
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                delivery.putSerializable(KEY_USUARIO, usuarioActual)
                delivery.putSerializable(KEY_PERSONA, personaActual)

                view.findNavController().navigate(R.id.menuFragment,delivery)
            } else {
                // Mostrar mensaje de error o realizar otra acción según necesites
                //Toast.makeText(requireContext(), "Datos incorrectos, intente de nuevo", Toast.LENGTH_SHORT).show()
                showCustomSnackBar(requireView(),  "Datos incorrectos", "Intente de nuevo")
            }
        }
    }

}