package com.example.tpago2.gui.gestionTarjetas

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tpago2.R
import com.example.tpago2.data.dao.TarjetaUsuarioDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.gui.utilitarios.showCustomSnackBar
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.util.Locale

class AgregarTarjetaFragment : Fragment(R.layout.fragment_agregar_tarjeta) {

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
        val btnAdd = view.findViewById<Button>(R.id.btn_confirmar_agregar)
        val ediNumTarjeta = view.findViewById<EditText>(R.id.et_numTarjeta)
        val ediFecha = view.findViewById<TextView>(R.id.et_fecha)
        val ediCSV = view.findViewById<EditText>(R.id.et_csv)
        val btnBack = view.findViewById<ImageButton>(R.id.ibtn_atras_agregar_tarjeta)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        ediFecha.setOnClickListener {
            mostrarDatePicker(ediFecha, view)
        }

        btnAdd.setOnClickListener {
            val numTarjeta = ediNumTarjeta.text.toString()
            val fecha = ediFecha.text.toString()
            val csv = ediCSV.text.toString()

            if (numTarjeta.length != 16) {
                ediNumTarjeta.error = "El número de tarjeta debe tener 16 dígitos"
                return@setOnClickListener
            }

            if (fecha.isEmpty()) {
                ediFecha.error = "La fecha no puede estar vacía"
                return@setOnClickListener
            }

            if (csv.length != 3) {
                ediCSV.error = "El código CSV debe tener 3 dígitos"
                return@setOnClickListener
            }


            val daoTarjeta = TarjetaUsuarioDAO(view.context)

            // Validar que no este vinculada a nigun usuario a en la base de datos
            if (daoTarjeta.tarjetaExiste(numTarjeta)) {
                //Toast.makeText(requireContext(), "La tarjeta ya esta vinculada con una cuenta TPAGO", Toast.LENGTH_SHORT).show()
                showCustomSnackBar(requireView(),  "!Atención¡", "Tarjeta ya vinculada con una tarjeta TPAGO", 1)
                return@setOnClickListener
            }

            // Valida que pertenezca a VISA:


            daoTarjeta.insertarTarjetaUsuario(numTarjeta, cuentaActual.num_movil,fecha, csv)
            //Toast.makeText(view.context, "Se agrego la tarjeta correctamente!", Toast.LENGTH_LONG).show()
            showCustomSnackBar(requireView(), "Tarjeta agregada", "Se agrego la tarjeta correctamente!", 2)
            view.findNavController().popBackStack()
        }
    }

    private fun mostrarDatePicker(ediFecha: TextView, view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val currentDateTime = LocalDateTime.now()

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Formatear la fecha seleccionada al formato deseado
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            calendar.set(selectedYear, selectedMonth, selectedDay)
            val fechaSeleccionada = dateFormat.format(calendar.time)
            val date = currentDateTime.toLocalDate().toString()

            if (fechaSeleccionada >= date) {
                // Establecer la fecha formateada en el TextView
                ediFecha.error = null
                ediFecha.text = fechaSeleccionada
                //showCustomSnackBar(view, "¡Atención!", "Fecha seleccionada: $fechaSeleccionada", 2)
            } else {
                ediFecha.error = "Fecha expirada"
                ediFecha.text = ""
                showCustomSnackBar(view, "¡Error!", "Fecha expirada", 1)
            }

        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }
}