package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class ListarContactosFragment : Fragment(R.layout.fragment_listar_contactos) {

    private lateinit var userAdapter: ContactoAdapter

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
        }

        initRecycleView(view)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnTest = view.findViewById<FloatingActionButton>(R.id.btnNumber)
        btnTest.setOnClickListener{
            //tiene que llevar el objeto Usuario(hayq ue hacer este usuario Serializable)

            view.findNavController().navigate(R.id.action_listarContactosFragment_to_pagarFragment)
        }
    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        userAdapter = ContactoAdapter(ContactoProvider.listaContactos) {user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }


    private fun onItemSelected(user:Contacto) {
        val daoCueUsu = CuentaUsuarioDAO(requireContext())
        val cuentaDestino = daoCueUsu.obtenerUsuarioDestinoPorNumMovil(user.numMovil)
        if (cuentaDestino != null && user.numMovil != cuentaActual.num_movil) {
            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            delivery.putSerializable(KEY_USUARIO_DESTINO, cuentaDestino)
            view?.findNavController()?.navigate(R.id.action_listarContactosFragment_to_pagarFragment, delivery)
        }
        else if (user.numMovil == cuentaActual.num_movil) {
            Toast.makeText(requireContext(), "No puede trasferirse a si mismo", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(requireContext(), "El contacto ${user.nombres} no pertence aa TPAGO", Toast.LENGTH_SHORT).show()
        }
    }
}