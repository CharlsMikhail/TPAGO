package com.example.tpago2.gui.gestionTarjetas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
import com.example.tpago2.gui.utilitarios.showCustomSnackBar
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_TARJETA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO
import java.time.LocalDateTime

class VerTarjetasFragment : Fragment(R.layout.fragment_ver_tarjetas) {

    private lateinit var targetAdapter: TarjetaAdapter

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var montoRecarga: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            montoRecarga = it.getString(KEY_MONTO_RECARGA) as String
        }

        eventos(view)


        initRecycleView(view)
    }

    private fun eventos(view: View) {
        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras_ver_tarjeta)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        val btnAgregarTarjet = view.findViewById<Button>(R.id.button2)

        btnAgregarTarjet.setOnClickListener{
            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            view.findNavController().navigate(R.id.action_verTarjetasFragment_to_agregarTarjetaFragment, delivery)
        }

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

    private fun onItemSelected(tarjeta: TarjetaUsuario) {

        val currentDateTime = LocalDateTime.now()
        val date = currentDateTime.toLocalDate().toString()

        if (tarjeta.fecha_vencimiento < date) {
            //Toast.makeText(requireContext(), "Fecha tarjeta expirada", Toast.LENGTH_SHORT).show()
            showCustomSnackBar(requireView(), "¡Atención!", "Fecha tarjeta expirada", 1)
            return
        }


        val delivery = Bundle()
        delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
        delivery.putSerializable(KEY_USUARIO, usuarioActual)
        delivery.putSerializable(KEY_PERSONA, personaActual)
        delivery.putString(KEY_MONTO_RECARGA, montoRecarga)
        delivery.putSerializable(KEY_TARJETA, tarjeta)
        requireView().findNavController().navigate(R.id.action_verTarjetasFragment_to_confirmarPagoFragment, delivery)
    }
}