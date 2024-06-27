package com.example.tpago2.gui.gestionChamba

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.AccesoAdapter
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.entidades.AccesoAndChamba
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_ACCESO
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaChambasFragment : Fragment(R.layout.fragment_lista_chambas), SearchView.OnQueryTextListener {
    private lateinit var userAdapter: AccesoAdapter
    private lateinit var searchView: SearchView

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

        searchView = view.findViewById(R.id.searchView)

        initRecycleView(view)
        actualizarInterfaz(view)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras)
        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        searchView.setOnQueryTextListener(this)

    }

    private fun actualizarInterfaz(view: View) {

    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        val daoAccesos = AccesoEmpleadoDAO(view.context)
        val listaAccesos = daoAccesos.obtenerAccesosPorEmpleado(cuentaActual.num_movil)
        userAdapter = AccesoAdapter(
            listaAccesos,
            R.layout.item_acceso
        ) { user -> onItemSelected(user) } //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user: AccesoAndChamba) {
        Toast.makeText(
            requireContext(),
            "${user.nombres} tienes acceso a su historial",
            Toast.LENGTH_SHORT
        ).show()

        val delivery = Bundle()
        delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
        delivery.putSerializable(KEY_USUARIO, usuarioActual)
        delivery.putSerializable(KEY_PERSONA, personaActual)
        delivery.putSerializable(KEY_ACCESO, user)

        requireView().findNavController().navigate(R.id.action_listaChambasFragment2_to_movimientosChambaFragment, delivery)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        userAdapter.filtrado(newText)
        return false
    }


}