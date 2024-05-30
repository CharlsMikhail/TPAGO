package com.example.tpago2.gui.realizarOperacion

import android.content.Context
import android.os.Bundle
import android.text.Editable
import java.time.LocalDateTime
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.OperacionDAO
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
import com.example.tpago2.service.falla
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

class PagarFragment : Fragment(R.layout.fragment_pagar) {

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var cuentaDestino: CuentaDestino
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
        }
        eventos(view)
        actualizarInterfaz(view)
    }

    private fun actualizarInterfaz(view: View) {
        val txtNombreUsuarioDestino = view.findViewById<TextView>(R.id.txt_nombre_usuario)
        txtNombreUsuarioDestino.text = cuentaDestino.nombres
    }

    private fun eventos(view: View) {
        val btnPagar = view.findViewById<Button>(R.id.btn_pagar)
        val txtMonto = view.findViewById<EditText>(R.id.txt_monto)

        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }


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


        btnPagar.setOnClickListener{
            if (falla) {
                mostrarErrorDeConexion(requireContext())
                return@setOnClickListener
            }
            if (txtMonto.text.isEmpty()) {
                Toast.makeText(requireContext(), "Escriba un monto, mínimo 1 sol", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val monto = txtMonto.text.toString().toInt()
            if ((monto <= 500) and (monto <= cuentaActual.saldo)) {

                realizarOperacion(monto)

                cuentaDestino.nombres = cuentaDestino.nombres.trim().split(" ").dropLast(1).joinToString(" ")


                val delivery = Bundle()
                delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                delivery.putSerializable(KEY_USUARIO, usuarioActual)
                delivery.putSerializable(KEY_PERSONA, personaActual)
                delivery.putSerializable(KEY_USUARIO_DESTINO, cuentaDestino)
                delivery.putString(KEY_MONTO_PAGO, monto.toString())
                delivery.putString(KEY_DATE_OPER, date)
                delivery.putString(KEY_TIME_OPER, hour)
                delivery.putString(KEY_NUM_OPER, numOperacion)
                view.findNavController()
                    .navigate(R.id.action_pagarFragment_to_detalleOperacionFragment, delivery)
            }
            else if (monto > 500) {
               Toast.makeText(requireContext(), "Límite de monto superado", Toast.LENGTH_LONG).show()
            }
            else if (monto > cuentaActual.saldo) {
                saldoIsuiciente()
            }
        }
    }

    fun mostrarErrorDeConexion(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error de Conexión")
        builder.setMessage("No se pudo realizar la operación. Por favor, verifica tu conexión a Internet e inténtalo de nuevo.")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            exitProcess(1)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun realizarOperacion(monto: Int) {
        val cuentaDao = CuentaUsuarioDAO(this.requireContext()) //ojo
        val currentDateTime = LocalDateTime.now()
        // QUITAR SALDO
        cuentaDao.actualizarSaldo(cuentaActual.num_movil, monto, false)

        //INSERTAR SALDO
        cuentaDao.actualizarSaldo(cuentaDestino.numMovil, monto, true)

        // INSERTAR OPERACION
        val formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss")
        val horaMinutoString = currentDateTime.format(formatterHora)

        date = currentDateTime.toLocalDate().toString()
        hour = horaMinutoString

        val operDao = OperacionDAO(this.requireContext())
        numOperacion = operDao.insertarOperacion(cuentaActual.num_movil, cuentaDestino.numMovil, horaMinutoString, currentDateTime.toLocalDate().toString(), monto).toString()

    }

    private fun saldoIsuiciente() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Saldo Insuficiente")
        dialog.setMessage("¿Desea realizar una recarga?")
        dialog.setPositiveButton("Recargar") { dialog, which ->
            Toast.makeText(requireContext(), "Recargar" , Toast.LENGTH_SHORT).show()
            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            requireView().findNavController().popBackStack()
            requireView().findNavController().popBackStack()
            requireView().findNavController().navigate(R.id.recargarFragment, delivery)
        }
        dialog.setNegativeButton("Volver al menú") { dialog, which ->
            requireView().findNavController().popBackStack()
            requireView().findNavController().popBackStack()
            Toast.makeText(requireContext(), "Operación cancelada", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }
}