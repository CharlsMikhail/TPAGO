package com.example.tpago2.gui.recargarSaldo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CODIGO_PE
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO

class DetallePagoEfectivoFragment : Fragment(R.layout.fragment_detalle_pago_efectivo) {

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var montoRecarga: String
    private lateinit var codigoGenerado: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            montoRecarga = it.getString(KEY_MONTO_RECARGA) as String
            codigoGenerado = it.getString(KEY_CODIGO_PE) as String
        }

        actualizarinterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnBack  = view.findViewById<Button>(R.id.btn_volver_menu_pe2)
        btnBack.setOnClickListener {
            view.findNavController().popBackStack(R.id.menuFragment,false)
        }
    }
    private fun actualizarinterfaz(view: View) {
        val txt_MontoPE = view.findViewById<TextView>(R.id.monto_pe)
        val codigoCIP = view.findViewById<TextView>(R.id.codigo_cip)
        txt_MontoPE.text = "S/" + montoRecarga
        codigoCIP.text = codigoGenerado
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