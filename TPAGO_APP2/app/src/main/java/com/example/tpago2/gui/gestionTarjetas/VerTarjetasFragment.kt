package com.example.tpago2.gui.gestionTarjetas

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
import com.example.tpago2.adapter.TarjetaAdapter
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.TarjetaUsuarioDAO
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

class VerTarjetasFragment : Fragment(R.layout.fragment_ver_tarjetas) {

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
    }

    private fun initRecycleView(view: View) {
        val targetDAO = TarjetaUsuarioDAO(view.context)
        val manager = LinearLayoutManager(context)
        targetAdapter = TarjetaAdapter(targetDAO.obtenerTarjetasUsuarioPorNumeroMovil(cuentaActual.num_movil), R.layout.item_tarjeta) { user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.ver_tarjetas)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = targetAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user: TarjetaUsuario) {
        Toast.makeText(context, "Vence en " + user.fecha_vencimiento, Toast.LENGTH_SHORT).show()
    }
}