package com.example.tpago2.gui.recargarSaldo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.RecargaDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.TarjetaUsuario
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.gui.utilitarios.mostrarErrorDeConexion
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_DATE_RECARGA
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_NUM_RECARGA
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_TARJETA
import com.example.tpago2.service.KEY_TIME_RECARGA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.falla
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConfirmarPagoFragment : Fragment(R.layout.fragment_confirmar_pago) {
    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var montoRecarga: String
    private lateinit var tarjetaRecarga: TarjetaUsuario
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            montoRecarga = it.getString(KEY_MONTO_RECARGA) as String
            tarjetaRecarga = it.getSerializable(KEY_TARJETA) as TarjetaUsuario
        }

        eventos(view)


    }

    private fun eventos(view: View) {
        val btnContinuarRecarga = view.findViewById<Button>(R.id.volver_menu_pe)
        val txtTarjeta = view.findViewById<TextView>(R.id.txt_tarjeta_confirmacion)
        val txtMonto = view.findViewById<TextView>(R.id.monto_pe)
        val btnBack = view.findViewById<ImageButton>(R.id.btn_confirmar_atras)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        val numTarjeta = tarjetaRecarga.num_tarjeta
        val maskedNumTarjeta = numTarjeta.takeLast(4).padStart(numTarjeta.length, '*')
        val formattedMaskedNumTarjeta = maskedNumTarjeta.chunked(4).joinToString(" ")
        txtTarjeta.text = formattedMaskedNumTarjeta

        txtMonto.text = "S/" + montoRecarga

        btnContinuarRecarga.setOnClickListener {
            if (falla) {
                mostrarErrorDeConexion(requireContext())
                return@setOnClickListener
            }

            val cuentaDao = CuentaUsuarioDAO(this.requireContext()) //ojo
            val currentDateTime = LocalDateTime.now()

            //INSERTAR SALDO
            cuentaDao.actualizarSaldo(cuentaActual.num_movil, montoRecarga.toInt(), true)

            // INSERTAR RECARGA
            val formatterHora = DateTimeFormatter.ofPattern("HH:mm")
            val horaMinutoString = currentDateTime.format(formatterHora)

            val date = currentDateTime.toLocalDate().toString()

            val daoRecarga = RecargaDAO(view.context)
            val numRecarga = daoRecarga.insertarRecarga(
                "tarjeta",
                tarjetaRecarga.num_tarjeta,
                null,
                montoRecarga.toInt(),
                date,
                horaMinutoString
            )

            Toast.makeText(view.context, "Se recargo exitosamente", Toast.LENGTH_SHORT).show()

            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            delivery.putSerializable(KEY_TARJETA, tarjetaRecarga)
            delivery.putString(KEY_MONTO_RECARGA, montoRecarga)
            delivery.putString(KEY_DATE_RECARGA, date)
            delivery.putString(KEY_TIME_RECARGA, horaMinutoString)
            delivery.putString(KEY_NUM_RECARGA, numRecarga.toString())
            view.findNavController().navigate(R.id.action_confirmarPagoFragment_to_detalleRecargaFragment, delivery)

        }
    }
}