package com.example.tpago2.gui.menuPrincipal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Cliente
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.User
import com.example.tpago2.data.entidades.Usuario

class MenuFragment : Fragment(R.layout.fragment_menu) {


    // Crear una instancia de Persona
    val persona = Persona(
        dniPersona = "12345678",
        primerNombre = "Juan",
        segundoNombre = "Carlos",
        apePaterno = "Pérez",
        apeMaterno = "Gómez"
    )

    // Crear una instancia de Cliente
    val cliente = Cliente(
        persona = persona,
        historialCliente = "Historial del cliente"
    )

    // Crear una instancia de Usuario
    val usuario = User(
        cliente = cliente,
        numMovil = "987654321",
        fechaInicio = "23/05/2023",
        observaciones = "Sin observaciones"
    )

    // Crear una instancia de CuentaUsuario
    val cuentaUsuario = CuentaUsuario(
        usuario = usuario,
        saldo = 1000,
        ipCuenta = "192.168.1.1",
        contrasenia = "password123",
        limitePorDia = 500,
        email = "usuario@example.com",
        estado = true
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actualizarInterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnPagar = view.findViewById<LinearLayout>(R.id.ll_pagar)
        btnPagar.setOnClickListener{
            view.findNavController().navigate(R.id.action_menuFragment_to_listarContactosFragment)
        }
    }

    private fun actualizarInterfaz(view: View) {
        val txtNameUser = view.findViewById<TextView>(R.id.txt_user_name)
        txtNameUser.text = "Hola, " + cuentaUsuario.usuario.cliente.persona.primerNombre
    }

}