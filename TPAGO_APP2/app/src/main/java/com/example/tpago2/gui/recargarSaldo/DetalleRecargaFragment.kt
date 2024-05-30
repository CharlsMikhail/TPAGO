package com.example.tpago2.gui.recargarSaldo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
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
        val btnBack  = view.findViewById<Button>(R.id.btn_inicio_recarga)
        btnBack.setOnClickListener {
            view.findNavController().popBackStack(R.id.menuFragment,false)
        }
    }
    private fun actualizarInterfaz(view: View) {
        val txtNumTarjeta = view.findViewById<TextView>(R.id.txt_num_origen)
        val txtNumDestino = view.findViewById<TextView>(R.id.txt_num_destino)
        val txtNumRecarga = view.findViewById<TextView>(R.id.txt_num_operacion)
        val txtDateOper = view.findViewById<TextView>(R.id.txt_fecha)
        val txtHourOper = view.findViewById<TextView>(R.id.txt_hora)
        val txtMontoOpe = view.findViewById<TextView>(R.id.txt_monto_ope)
        val txtMontoPago = view.findViewById<TextView>(R.id.txt_monto_pago)

        txtNumTarjeta.text = tarjetaRecarga.num_tarjeta
        txtNumDestino.text = cuentaActual.num_movil.toString()
        txtNumRecarga.text = numRecarga
        txtDateOper.text = date
        txtHourOper.text = hour
        txtMontoOpe.text = montoRecarga
        txtMontoPago.text = montoRecarga
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireView().findNavController().popBackStack(R.id.menuFragment,false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }


}