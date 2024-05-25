package com.example.tpago2.gui.aperturarCuenta

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario

class IngresarSesionFragment : Fragment(R.layout.fragment_ingresar_sesion) {
    // Estos datos se supone que ya los tengo...
    val personaActual = Persona(12345678,"Alicia","María","González","López")
    val usuarioActual = Usuario(12345678,"2024-05-01","Usuario activo")
    val cuentaActual = CuentaUsuario(123456789,12345678,10000,"192.168.2.1",123224,1000,"user1@example.com",1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnIniciar = view.findViewById<Button>(R.id.btn_iniciar)


        btnIniciar.setOnClickListener {
            val txtKey = view.findViewById<EditText>(R.id.txt_key)
            if (validarDatos(txtKey.text.toString().toInt())) {
                view.findNavController().navigate(R.id.action_ingresarSesionFragment_to_menuFragment)
            } else {
                Toast.makeText(requireActivity(), "Contraseña incorrecta", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validarDatos(key: Int): Boolean {
        return this.cuentaActual.contrasenia == key
    }

}