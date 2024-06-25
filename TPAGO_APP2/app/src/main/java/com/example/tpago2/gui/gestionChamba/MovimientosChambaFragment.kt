package com.example.tpago2.gui.gestionChamba
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.MovimientosAdapter
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.dao.OperacionDAO
import com.example.tpago2.data.entidades.AccesoAndChamba
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Movimiento
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.gui.utilitarios.showCustomSnackBar
import com.example.tpago2.service.KEY_ACCESO
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO

class MovimientosChambaFragment : Fragment(R.layout.fragment_movimientos_chamba) {

    private lateinit var moviAdapter: MovimientosAdapter

    private lateinit var cuentaActual: CuentaUsuario
    private lateinit var usuarioActual: Usuario
    private lateinit var personaActual: Persona
    private lateinit var acceso: AccesoAndChamba

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cuentaActual = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuarioActual = it.getSerializable(KEY_USUARIO) as Usuario
            personaActual = it.getSerializable(KEY_PERSONA) as Persona
            acceso = it.getSerializable(KEY_ACCESO) as AccesoAndChamba
        }
        eventos(view)
        actualizarUI(view)
        initRecycleView(view)
    }

    private fun actualizarUI(view: View) {
        val nombreChamba = view.findViewById<TextView>(R.id.user_acceso)
        val numberChamba = view.findViewById<TextView>(R.id.acceso_number)
        nombreChamba.text = acceso.nombres
        numberChamba.text = acceso.numMovil.toString()
    }

    private fun eventos(view: View) {
        val btnBack = view.findViewById<ImageButton>(R.id.btn_atras)
        btnBack.setOnClickListener() {
            view.findNavController().popBackStack()
        }

    }


    private fun initRecycleView(view: View) {
        val operDAO = OperacionDAO(view.context)
        val manager = LinearLayoutManager(context)
        val listaMovimientos = operDAO.obtenerMovimientosPorCuenta(acceso.numMovil)
        if (listaMovimientos.isEmpty()) {
            //Toast.makeText(context, "No hay movimientos", Toast.LENGTH_LONG).show()
            showCustomSnackBar(requireView(),  "!Atención¡", "No hay movimientos")
            return
        }
        moviAdapter = MovimientosAdapter(listaMovimientos) { user -> onItemSelected(user) } //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_mov_chamba)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = moviAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user: Movimiento) {
        //Toast.makeText(requireActivity(), user.nombre, Toast.LENGTH_SHORT)
    }

}