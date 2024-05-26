package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.UsuariosAdapter
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.UsuarioProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListarContactosFragment : Fragment(R.layout.fragment_listar_contactos) {

    private lateinit var userAdapter: UsuariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        userAdapter = UsuariosAdapter(ContactoProvider.listaContactos) //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }

}