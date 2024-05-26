package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.service.ContactoProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class ListarContactosFragment : Fragment(R.layout.fragment_listar_contactos) {

    private lateinit var userAdapter: ContactoAdapter

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
        userAdapter = ContactoAdapter(ContactoProvider.listaContactos) {user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }


    private fun onItemSelected(user:Contacto) {
        Toast.makeText(requireActivity(), user.nombres, Toast.LENGTH_LONG).show()
    }


}