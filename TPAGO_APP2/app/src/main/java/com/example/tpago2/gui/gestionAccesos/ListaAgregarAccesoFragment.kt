package com.example.tpago2.gui.gestionAccesos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.AccesoAdapter
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.entidades.AccesoAndChamba
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO

class ListaAgregarAccesoFragment : Fragment(R.layout.fragment_lista_agregar_acceso) {

    private lateinit var userAdapter: ContactoAdapter
    private lateinit var listaAcceso: MutableList<Contacto>

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

        initRecycleView(view)
        actualizarInterfaz(view)
        eventos(view)

        actualizarInterfaz(view)
        eventos(view)
    }

    private fun eventos(view: View) {

    }

    private fun actualizarInterfaz(view: View) {
    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        userAdapter = ContactoAdapter(true, ContactoProvider.listaContactos) {user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user: Contacto) {
        //listaAcceso.add(user)
    }


}