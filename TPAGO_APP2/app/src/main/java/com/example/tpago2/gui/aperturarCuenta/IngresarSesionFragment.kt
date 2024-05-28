package com.example.tpago2.gui.aperturarCuenta

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import kotlin.system.exitProcess

class IngresarSesionFragment : Fragment(R.layout.fragment_ingresar_sesion) {
    // Estos datos se supone que ya los tengo...
    private val personaActual = Persona(72866150,"Carlos","Leandro","Vilca","Estrada")
    private val usuarioActual = Usuario(72866150,"2023-06-03","La fe")
    private val cuentaActual = CuentaUsuario(986579487,72866150,3000,"181.64.192.10,123224",123224,500,"pepito.juarez@gmail.com",1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnIniciar = view.findViewById<ImageButton>(R.id.btn_iniciar)

        btnIniciar.setOnClickListener {
            val txtKey = view.findViewById<EditText>(R.id.txt_key)
            if (txtKey.text.isNotEmpty()) {
                if (validarDatos(txtKey.text.toString().toInt())) {
                    val delivery = Bundle()
                    delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                    delivery.putSerializable(KEY_USUARIO, usuarioActual)
                    delivery.putSerializable(KEY_PERSONA, personaActual)
                    view.findNavController()
                        .navigate(R.id.action_ingresarSesionFragment_to_menuFragment, delivery)
                } else {
                    Toast.makeText(requireActivity(), "Contraseña incorrecta", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireActivity(), "Ingrese su contraseña", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validarDatos(key: Int): Boolean {
        return this.cuentaActual.contrasenia == key
    }



}