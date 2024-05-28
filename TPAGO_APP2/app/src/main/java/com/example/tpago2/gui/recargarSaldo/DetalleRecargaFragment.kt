package com.example.tpago2.gui.recargarSaldo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaDestino
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.TarjetaUsuario
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_DATE_OPER
import com.example.tpago2.service.KEY_DATE_RECARGA
import com.example.tpago2.service.KEY_MONTO_PAGO
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_NUM_OPER
import com.example.tpago2.service.KEY_NUM_RECARGA
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_TARJETA
import com.example.tpago2.service.KEY_TIME_OPER
import com.example.tpago2.service.KEY_TIME_RECARGA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO

class DetalleRecargaFragment : Fragment(R.layout.fragment_detalle_recarga) {
    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var tarjetaRecarga: TarjetaUsuario
    private lateinit var montoRecarga: String
    private lateinit var numRecarga: String
    private lateinit var date: String
    private lateinit var hour: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            tarjetaRecarga = it.getSerializable(KEY_TARJETA) as TarjetaUsuario
            montoRecarga = it.getString(KEY_MONTO_RECARGA) as String
            numRecarga = it.getString(KEY_NUM_RECARGA) as String
            date = it.getString(KEY_DATE_RECARGA) as String
            hour = it.getString(KEY_TIME_RECARGA) as String
        }
        actualizarInterfaz(view)
        eventos(view)
    }

    private fun eventos(view: View) {

    }

    private fun actualizarInterfaz(view: View) {

    }
}