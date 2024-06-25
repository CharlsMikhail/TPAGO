package com.example.tpago2.gui.realizarOperacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.ContactoAdapter
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.ContactoProvider
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.example.tpago2.service.KEY_USUARIO_DESTINO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.example.tpago2.gui.utilitarios.showCustomSnackBar

class ListarContactosFragment : Fragment(R.layout.fragment_listar_contactos) {

    private lateinit var userAdapter: ContactoAdapter

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
        }

        initRecycleView(view)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras)

        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

        val btnNumber = view.findViewById<FloatingActionButton>(R.id.btnNumber)
        btnNumber.setOnClickListener{
            showDialogNumber(view)
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
        dialog.setPositiveButton("Buscar", null)

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
                input.error = "Por favor, ingrese un número"
            } else if (!isValidPhoneNumber(inputNumber)) {
                input.error = "Celular debe contener 9 dígitos y comenzar con el '9'"
            } else {
                val daoCueUsu = CuentaUsuarioDAO(requireContext())
                val cuentaDestino = daoCueUsu.obtenerUsuarioDestinoPorNumMovil(inputNumber.toInt())
                if (cuentaDestino != null && inputNumber.toInt() != cuentaActual.num_movil) {
                    val delivery = Bundle().apply {
                        putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
                        putSerializable(KEY_USUARIO, usuarioActual)
                        putSerializable(KEY_PERSONA, personaActual)
                        putSerializable(KEY_USUARIO_DESTINO, cuentaDestino)
                    }
                    view.findNavController().navigate(R.id.action_listarContactosFragment_to_pagarFragment, delivery)
                    alertDialog.dismiss() // Cerrar el diálogo si la operación es exitosa
                } else if (inputNumber.toInt() == cuentaActual.num_movil) {
                    //Toast.makeText(requireContext(), "No puede transferirse a sí mismo.", Toast.LENGTH_SHORT).show()
                    showCustomSnackBar(dialogView,  "¡Atención!", "No puede transferirse a sí mismo")

                } else {
                    //Toast.makeText(requireContext(), "El número ${inputNumber.toInt()} no pertenece a TPAGO.", Toast.LENGTH_SHORT).show()
                    //Snackbar.make(requireView(), "El número ${inputNumber.toInt()} no pertenece a TPAGO.", Snackbar.LENGTH_SHORT).show()
                    showCustomSnackBar(dialogView,  "¡Atención!", "El número ${inputNumber.toInt()} no pertenece a TPAGO")
                }
            }
        }
    }


    // Función para validar el número de teléfono (simple ejemplo)
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberPattern = "^9\\d{8}$"
        return phoneNumber.matches(phoneNumberPattern.toRegex())
    }


    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        userAdapter = ContactoAdapter(null, ContactoProvider.listaContactos) {user -> onItemSelected(user)} //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_usuarios)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }


    private fun onItemSelected(user:Contacto) {
        val daoCueUsu = CuentaUsuarioDAO(requireContext())
        val cuentaDestino = daoCueUsu.obtenerUsuarioDestinoPorNumMovil(user.numMovil)
        if (cuentaDestino != null && user.numMovil != cuentaActual.num_movil) {
            val delivery = Bundle()
            delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
            delivery.putSerializable(KEY_USUARIO, usuarioActual)
            delivery.putSerializable(KEY_PERSONA, personaActual)
            delivery.putSerializable(KEY_USUARIO_DESTINO, cuentaDestino)
            view?.findNavController()?.navigate(R.id.action_listarContactosFragment_to_pagarFragment, delivery)
        }
        else if (user.numMovil == cuentaActual.num_movil) {
            //Toast.makeText(requireContext(), "No puede trasferirse a si mismo", Toast.LENGTH_SHORT).show()
            showCustomSnackBar(requireView(),  "¡Atención!", "No puede transferirse a sí mismo")
        }
        else {
            //Toast.makeText(requireContext(), "El contacto ${user.nombres} no pertence a TPAGO", Toast.LENGTH_SHORT).show()
            showCustomSnackBar(requireView(),  "¡Atención!", "El contacto ${user.nombres.toUpperCase()} no pertenece a TPAGO")

        }
    }
}