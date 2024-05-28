package com.example.tpago2.gui.recargarSaldo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO

class MetodoRecargaFragment : Fragment(R.layout.fragment_metodo_recarga) {

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var montoRecarga: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)

        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            montoRecarga = it.getString(KEY_MONTO_RECARGA) as String
        }

    }

    private fun eventos(view: View) {
        val btnContinuar = view.findViewById<Button>(R.id.btn_continuar_recarga)
        val rbTarjeta = view.findViewById<RadioButton>(R.id.rbtn_tarjeta)
        val rbPagoEfectivo = view.findViewById<RadioButton>(R.id.rbtn_pagoefectivo)

        btnContinuar.setOnClickListener {
            if (rbTarjeta.isChecked) {
                val delivery = Bundle()
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                delivery.putSerializable(KEY_USUARIO, usuarioActual)
                delivery.putSerializable(KEY_PERSONA, personaActual)
                delivery.putString(KEY_MONTO_RECARGA, montoRecarga)
                view.findNavController().navigate(R.id.action_metodoRecargaFragment_to_verTarjetasFragment, delivery)

            } else if (rbPagoEfectivo.isChecked) {
                val delivery = Bundle()
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                delivery.putSerializable(KEY_USUARIO, usuarioActual)
                delivery.putSerializable(KEY_PERSONA, personaActual)
                delivery.putString(KEY_MONTO_RECARGA, montoRecarga)
                view.findNavController().navigate(R.id.action_metodoRecargaFragment_to_detallePagoEfectivoFragment, delivery)

            } else {
                Toast.makeText(view.context, "Seleccione una opcion", Toast.LENGTH_LONG).show()
            }
        }



    }

}