package com.example.tpago2.gui.recargarSaldo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.RecargaDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CODIGO_PE
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_MONTO_RECARGA
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class MetodoRecargaFragment : Fragment(R.layout.fragment_metodo_recarga) {

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var montoRecarga: String
    private lateinit var codigoGenerado: String

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
        val btnContinuar = view.findViewById<Button>(R.id.volver_menu_pe)
        val rbTarjeta = view.findViewById<RadioButton>(R.id.rbtn_tarjeta)
        val rbPagoEfectivo = view.findViewById<RadioButton>(R.id.rbtn_pagoefectivo)
        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras_metodo)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }



        btnContinuar.setOnClickListener {
            if (rbTarjeta.isChecked) {
                val delivery = Bundle()
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                delivery.putSerializable(KEY_USUARIO, usuarioActual)
                delivery.putSerializable(KEY_PERSONA, personaActual)
                delivery.putString(KEY_MONTO_RECARGA, montoRecarga)
                view.findNavController().navigate(R.id.action_metodoRecargaFragment_to_verTarjetasFragment, delivery)

            } else if (rbPagoEfectivo.isChecked) {

                eventoPagoEfectivo()

                val delivery = Bundle()
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                delivery.putSerializable(KEY_USUARIO, usuarioActual)
                delivery.putSerializable(KEY_PERSONA, personaActual)
                delivery.putString(KEY_MONTO_RECARGA, montoRecarga)
                delivery.putString(KEY_CODIGO_PE, codigoGenerado)
                view.findNavController().navigate(R.id.action_metodoRecargaFragment_to_detallePagoEfectivoFragment, delivery)

            } else {
                Toast.makeText(view.context, "Seleccione una opcion", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun eventoPagoEfectivo() {
        val cuentaDao = CuentaUsuarioDAO(this.requireContext()) //ojo

        val currentDateTime = LocalDateTime.now()

        //INSERTAR SALDO
        cuentaDao.actualizarSaldo(cuentaActual.num_movil, montoRecarga.toInt(), true)

        // INSERTAR RECARGA
        val formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss")
        val horaMinutoString = currentDateTime.format(formatterHora)

        val date = currentDateTime.toLocalDate().toString()

        codigoGenerado = Random.nextInt(0, 1_000_000_000).toString()

        val daoRecarga = RecargaDAO(requireContext())
        val numRecarga = daoRecarga.insertarRecarga(
            "tarjeta",
            null,
            codigoGenerado,
            montoRecarga.toInt(),
            date,
            horaMinutoString
        )

        Toast.makeText(requireContext(), "Orden de pago generado", Toast.LENGTH_SHORT).show()

    }

}