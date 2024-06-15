package com.example.tpago2.gui.gestionAccesos

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.isAdd
import com.example.tpago2.service.restanteAccesos
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import kotlin.properties.Delegates

class ListaAgregarAccesoFragment : Fragment(R.layout.fragment_lista_agregar_acceso) {

    private lateinit var userAdapter: ContactoAdapter
    private var listaAcceso: MutableList<Contacto> = mutableListOf()
    private lateinit var daoAccesosEmpleado: AccesoEmpleadoDAO

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

        // Obtener  el limite de restante de accesos
        daoAccesosEmpleado = AccesoEmpleadoDAO(requireContext())
        restanteAccesos = 10 - daoAccesosEmpleado.obtenerCantidadEmpleadosVinculados(cuentaActual.num_movil)

        initRecycleView(view)
        actualizarInterfaz(view)
        eventos(view)

        actualizarInterfaz(view)
        eventos(view)
    }

    private fun eventos(view: View) {

        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras)
        btnBack.setOnClickListener() {
            listaAcceso.forEach { contacto ->
                contacto.isAdd = false
            }
            view.findNavController().popBackStack()
        }

        val btnAddAccess = view.findViewById<FloatingActionButton>(R.id.btn_Add_Acceso)
        btnAddAccess.setOnClickListener{
            if (restanteAccesos > 0) {
                showDialogNumber(view)
            } else {
                Toast.makeText(context, "No hay más accesos disponibles", Toast.LENGTH_SHORT).show()
            }

        }

        val agregarAccesos = view.findViewById<Button>(R.id.add_button_accesos)

        agregarAccesos.setOnClickListener{
            if (listaAcceso.isNotEmpty()) {
                val currentDateTime = LocalDateTime.now()
                listaAcceso.forEach {contacto ->
                    contacto.isAdd = false
                    val numMovilEmpleador = cuentaActual.num_movil // Obtén el número de móvil del empleador de algún lugar
                    val numMovilEmpleado = contacto.numMovil
                    val estadoAcceso = 1// Define el estado del acceso según lo necesites
                    val fechaAcceso = currentDateTime.toLocalDate().toString()// Define la fecha de acceso según lo necesites

                    daoAccesosEmpleado.insertarAccesoEmpleado(numMovilEmpleador, numMovilEmpleado, estadoAcceso, fechaAcceso)
                }
                Toast.makeText(context, "Accesos agregados correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No hay usuarios para agregar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            view.findNavController().popBackStack()

        }



    }

    private fun actualizarInterfaz(view: View) {
    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)

        // El adapter tiene que recibir el maximo de usuarios que se pueden agregar y la lista de usuarios.
        userAdapter = ContactoAdapter(cuentaActual.num_movil, ContactoProvider.listaContactos) {user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user: Contacto) {
        if (user.isAdd) {
            //Toast.makeText(requireContext(), "Usuario agregado ${user.nombres}", Toast.LENGTH_SHORT).show()
            listaAcceso.add(user)
        } else {
            //Toast.makeText(requireContext(), "Usuario descartado", Toast.LENGTH_SHORT).show()
            listaAcceso.remove(user)
        }
    }

    private fun showDialogNumber(view: View) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Ingresar Número: ")

        // Inflar el layout del EditText desde el XML
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_number, null)

        // Obtener la referencia al EditText del layout inflado
        val input = dialogView.findViewById<EditText>(R.id.editTextPhoneNumber)

        dialog.setView(dialogView)

        // Configuramos el botón positivo sin comportamiento inicial para evitar que el diálogo se cierre automáticamente
        dialog.setPositiveButton("Agregar acceso", null)

        dialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_SHORT).show()
        }

        val alertDialog = dialog.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val inputNumber = input.text.toString()

            // Validar el número de teléfono ingresado
            if (inputNumber.isBlank()) {
                input.error = "Por favor, ingrese un número."
            } else if (!isValidPhoneNumber(inputNumber)) {
                input.error = "Celular debe contener 9 dígitos y comenzar con el '9'."
            } else if (listaAcceso.any { it.numMovil == inputNumber.toInt() }) {
                // Caso: El número ya está en la lista
                Toast.makeText(requireContext(), "El número ${inputNumber.toInt()} esta en la lista de espera.", Toast.LENGTH_SHORT).show()
            } else {
                val daoCueUsu = CuentaUsuarioDAO(requireContext())
                val cuentaDestino = daoCueUsu.obtenerUsuarioDestinoPorNumMovil(inputNumber.toInt())

                // Verificar si cuentaDestino no es nulo y cumplir con las condiciones requeridas
                if (cuentaDestino != null) {
                    val numMovilDestino = cuentaDestino.numMovil

                    if (inputNumber.toInt() == cuentaActual.num_movil) {
                        // Caso: No puede darse acceso a sí mismo
                        Toast.makeText(requireContext(), "No puede darse acceso a sí mismo.", Toast.LENGTH_SHORT).show()
                    } else if (daoAccesosEmpleado.verificarEmpleadoDelEmpleador(cuentaActual.num_movil, numMovilDestino)) {
                        // Caso: El número ya es empleado
                        Toast.makeText(requireContext(), "Este número ya forma parte de sus empleados.", Toast.LENGTH_SHORT).show()
                    } else if (!daoAccesosEmpleado.verificarEmpleadoDelEmpleador(cuentaActual.num_movil, numMovilDestino)) {
                        // Caso: Insertar acceso empleado
                        val currentDateTime = LocalDateTime.now()
                        val fechaAcceso = currentDateTime.toLocalDate().toString()

                        daoAccesosEmpleado.insertarAccesoEmpleado(cuentaActual.num_movil, numMovilDestino, 1, fechaAcceso)
                        restanteAccesos--
                        Toast.makeText(requireContext(), "Restante: $restanteAccesos", Toast.LENGTH_SHORT).show()
                        Toast.makeText(requireContext(), "Agregado exitosamente", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss() // Cerrar el diálogo si la operación es exitosa
                    }
                } else {
                    // Caso: El número no pertenece a TPAGO
                    Toast.makeText(requireContext(), "El número ${inputNumber.toInt()} no pertenece a TPAGO.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberPattern = "^9\\d{8}$"
        return phoneNumber.matches(phoneNumberPattern.toRegex())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                listaAcceso.forEach { contacto ->
                    contacto.isAdd = false
                }
                requireView().findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }


}