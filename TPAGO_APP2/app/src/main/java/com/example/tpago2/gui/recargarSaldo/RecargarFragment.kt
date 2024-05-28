package com.example.tpago2.gui.recargarSaldo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.entidades.CuentaDestino
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_DATE_OPER
import com.example.tpago2.service.KEY_MONTO_PAGO
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_NUM_OPER
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_TIME_OPER
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO

class RecargarFragment : Fragment(R.layout.fragment_recargar) {

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
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnRecargar = view.findViewById<Button>(R.id.btn_recargar)
        val txtMonto = view.findViewById<EditText>(R.id.txt_monto_recargar)

        //Mascarita
        txtMonto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }

            override fun afterTextChanged(s: Editable?) {
                // Check if the first character is '0'
                if (s != null && s.isNotEmpty() && s[0] == '0') {
                    // Remove the first character
                    txtMonto.setText(s.substring(1))
                    txtMonto.setSelection(txtMonto.text.length) // Move cursor to the end
                }
            }
        })

        btnRecargar.setOnClickListener{
            if (txtMonto.text.isEmpty()) {
                Toast.makeText(requireContext(), "Escriba un monto, mínimo 1 sol", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val monto = txtMonto.text.toString().toInt()
            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            delivery.putString(KEY_MONTO_RECARGA, monto.toString())

            view.findNavController().navigate(R.id.action_recargarFragment_to_metodoRecargaFragment, delivery)
        }

    }
}