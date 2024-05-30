package com.example.tpago2.gui.realizarOperacion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaDestino
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_DATE_OPER
import com.example.tpago2.service.KEY_MONTO_PAGO
import com.example.tpago2.service.KEY_NUM_OPER
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_TIME_OPER
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO

class DetalleOperacionFragment : Fragment(R.layout.fragment_detalle_operacion) {
    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var cuentaDestino: CuentaDestino
    private lateinit var monto: String
    private lateinit var numOperacion: String
    private lateinit var date: String
    private lateinit var hour: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            cuentaDestino = it.getSerializable(KEY_USUARIO_DESTINO) as CuentaDestino
            monto = it.getString(KEY_MONTO_PAGO) as String
            numOperacion = it.getString(KEY_NUM_OPER) as String
            date = it.getString(KEY_DATE_OPER) as String
            hour = it.getString(KEY_TIME_OPER) as String
        }
        actualizarInterfaz(view)
        eventos(view)

    }

    private fun actualizarInterfaz(view: View) {
        val txtNumOrigen = view.findViewById<TextView>(R.id.txt_num_origen)
        val txtNumDestino = view.findViewById<TextView>(R.id.txt_num_destino)
        val txtNumOperacion = view.findViewById<TextView>(R.id.txt_num_operacion)
        val txtDateOper = view.findViewById<TextView>(R.id.txt_fecha)
        val txtHourOper = view.findViewById<TextView>(R.id.txt_hora)
        val txtMontoOpe = view.findViewById<TextView>(R.id.txt_monto_ope)
        val txtNomApeDestino = view.findViewById<TextView>(R.id.txt_nom_ape_destino)
        val txtMontoPago = view.findViewById<TextView>(R.id.txt_monto_pago)

        txtNumOrigen.text = cuentaActual.num_movil.toString() + " -> " + personaActual.primer_nombre + " " + personaActual.ape_paterno
        txtNumDestino.text = cuentaDestino.numMovil.toString() + " -> " + cuentaDestino.nombres
        txtNumOperacion.text = numOperacion
        txtDateOper.text = date
        txtHourOper.text = hour
        txtMontoPago.text = monto
        txtMontoOpe.text = monto
        txtNomApeDestino.text = cuentaDestino.nombres
    }


    private fun eventos(view: View) {
        val btnInicio = view.findViewById<Button>(R.id.btn_inicio)
        /*val btnCompartir = view.findViewById<Button>(R.id.btn_compartir)*/

        btnInicio.setOnClickListener {
            view.findNavController().popBackStack(R.id.menuFragment,false)
        }
        /*
        btnCompartir.setOnClickListener {
            Toast.makeText(view.context, "Función en desarrollo por TPAGO, disculpe", Toast.LENGTH_SHORT).show()
        }*/

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