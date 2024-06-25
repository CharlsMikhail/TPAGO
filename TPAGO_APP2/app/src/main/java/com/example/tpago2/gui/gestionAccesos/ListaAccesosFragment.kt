package com.example.tpago2.gui.gestionAccesos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.AccesoAdapter
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.AccesoAndChamba
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
import kotlin.properties.Delegates

class ListaAccesosFragment : Fragment(R.layout.fragment_lista_accesos) {

    private lateinit var userAdapter: AccesoAdapter

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
    }

    private fun eventos(view: View) {
        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        val btnNumber = view.findViewById<FloatingActionButton>(R.id.btnNumber)
        btnNumber.setOnClickListener{

            //Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT).show()
            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            view.findNavController().navigate(R.id.action_listaAccesosFragment_to_listaAgregarAccesoFragment, delivery)
            val toast = Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT)
            toast.show()

            Handler(Looper.getMainLooper()).postDelayed({
                toast.cancel()
            }, 1000)
        }
    }

    private fun actualizarInterfaz(view: View) {

    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        val daoAccesos = AccesoEmpleadoDAO(view.context)
        val listaAccesos = daoAccesos.obtenerAccesosPorEmpleador(cuentaActual.num_movil)
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
            "El usuario ${user.nombres.uppercase()} tiene acceso a tu historial",
            Toast.LENGTH_SHORT
        ).show()
    }
}