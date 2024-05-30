package com.example.tpago2.gui.gestionTarjetas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.adapter.TarjetaAdapter
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.TarjetaUsuarioDAO
import com.example.tpago2.data.dao.UsuarioDAO
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.CuentaDestino
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.TarjetaUsuario
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListarTarjetasFragment : Fragment(R.layout.fragment_listar_tarjetas) {

    private lateinit var targetAdapter: TarjetaAdapter

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
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnAddTarjeta)
        val btnBack = view.findViewById<ImageButton>(R.id.btn_back_listar_tarjetas)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }


        val delivery = Bundle()
        delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
        delivery.putSerializable(KEY_USUARIO, usuarioActual)
        delivery.putSerializable(KEY_PERSONA, personaActual)

        btnAdd.setOnClickListener{
            val daoTarjeta = TarjetaUsuarioDAO(requireContext())
            if (daoTarjeta.obtenerTotalTarjetasPorUsuario(cuentaActual.num_movil) >= 4) {
                Toast.makeText(requireContext(), "Límite de tarjetas superado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            view.findNavController().navigate(R.id.action_listarTarjetasFragment_to_agregarTarjetaFragment, delivery)
        }
    }

    private fun initRecycleView(view: View) {
        val targetDAO = TarjetaUsuarioDAO(view.context)
        val manager = LinearLayoutManager(context)
        targetAdapter = TarjetaAdapter(targetDAO.obtenerTarjetasUsuarioPorNumeroMovil(cuentaActual.num_movil), R.layout.item_tarjeta_x) { user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_tarjetas_x)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = targetAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user: TarjetaUsuario) {
        Toast.makeText(context, "Vence en " + user.fecha_vencimiento, Toast.LENGTH_SHORT).show()
    }
}