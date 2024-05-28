package com.example.tpago2.gui.menuPrincipal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.MovimientosAdapter
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.dao.OperacionDAO
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.Movimiento
import com.example.tpago2.data.entidades.Persona
import com.example.tpago2.data.entidades.Usuario
import com.example.tpago2.service.KEY_CUENTA_USUARIO
import com.example.tpago2.service.KEY_PERSONA
import com.example.tpago2.service.KEY_USUARIO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.system.exitProcess

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var moviAdapter: MovimientosAdapter

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
        actualizarInterfaz(view)
        eventos(view)
    }

    private fun eventos(view: View) {
        val btnPagar = view.findViewById<FloatingActionButton>(R.id.fl_pagar)
        val delivery = Bundle()
        delivery.putSerializable(KEY_CUENTA_USUARIO, cuentaActual)
        delivery.putSerializable(KEY_USUARIO, usuarioActual)
        delivery.putSerializable(KEY_PERSONA, personaActual)

        btnPagar.setOnClickListener{
            view.findNavController().navigate(R.id.action_menuFragment_to_listarContactosFragment, delivery)
        }

        val txtMostrarSaldo = view.findViewById<TextView>(R.id.btn_mostrar_saldo)
        val txtSaldo = view.findViewById<TextView>(R.id.txt_saldo22)
        txtMostrarSaldo.setOnClickListener {
            val cuentaDao = CuentaUsuarioDAO(requireContext())
            //cuentaActual.saldo = cuentaDao.obtenerSaldo(cuentaActual.num_movil)
            cuentaActual.saldo = 5
            if (txtMostrarSaldo.text != "Ocultar Saldo") {
                txtSaldo.text = "S/" + cuentaActual.saldo
                txtMostrarSaldo.text = "Ocultar Saldo"
            }
            else {
                txtMostrarSaldo.text = "Mostrar Saldo"
                txtSaldo.text = "S/*******"
            }
        }

        val btnPagarQR = view.findViewById<LinearLayout>(R.id.ll_qr)
        btnPagarQR.setOnClickListener() {
            view.findNavController().navigate(R.id.action_menuFragment_to_qrFragment, delivery)
        }

        val btnPagarNFC = view.findViewById<LinearLayout>(R.id.ll_nfc)
        btnPagarNFC.setOnClickListener() {
            view.findNavController().navigate(R.id.action_menuFragment_to_nfcFragment, delivery)
        }

        val btnTarjetas = view.findViewById<LinearLayout>(R.id.ll_tarjetas)
        btnTarjetas.setOnClickListener() {
            view.findNavController().navigate(R.id.action_menuFragment_to_listarTarjetasFragment, delivery)
        }

        val btnRecargar = view.findViewById<LinearLayout>(R.id.ll_recargar)
        btnRecargar.setOnClickListener() {
            view.findNavController().navigate(R.id.action_menuFragment_to_recargarFragment, delivery)
        }

    }

    private fun actualizarInterfaz(view: View) {
        val txtNameUser = view.findViewById<TextView>(R.id.txt_user_name)
        txtNameUser.text = "Hola, " + personaActual.primer_nombre
    }

    private fun initRecycleView(view: View) {
        val operDAO = OperacionDAO(view.context)
        val manager = LinearLayoutManager(context)
        moviAdapter = MovimientosAdapter(operDAO.obtenerMovimientosPorCuenta(cuentaActual.num_movil)) { user -> onItemSelected(user) } //ojito
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.lista_movimientos)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = moviAdapter
        usersRecyler.addItemDecoration(decoration)
    }

    private fun onItemSelected(user:Movimiento) {
        Toast.makeText(requireActivity(), user.nombre, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                exitProcess(1)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }

}