package com.example.tpago2.gui.menuPrincipal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.adapter.MovimientosAdapter
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

    private lateinit var cuentaUsuario: CuentaUsuario
    private lateinit var usuario: Usuario
    private lateinit var persona: Persona

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cuentaUsuario = it.getSerializable(KEY_CUENTA_USUARIO) as CuentaUsuario
            usuario = it.getSerializable(KEY_USUARIO) as Usuario
            persona = it.getSerializable(KEY_PERSONA) as Persona
        }
        initRecycleView(view)
        actualizarInterfaz(view)
        eventos(view)

    }

    private fun eventos(view: View) {
        val btnPagar = view.findViewById<FloatingActionButton>(R.id.fl_pagar)
        btnPagar.setOnClickListener{
            view.findNavController().navigate(R.id.action_menuFragment_to_listarContactosFragment)
        }
    }

    private fun actualizarInterfaz(view: View) {
        val txtNameUser = view.findViewById<TextView>(R.id.txt_user_name)
        txtNameUser.text = "Hola, " + persona.primer_nombre

        val txtSaldo = view.findViewById<TextView>(R.id.txt_saldo22)
        txtSaldo.text = "S/." + cuentaUsuario.saldo
    }

    private fun initRecycleView(view: View) {
        val operDAO = OperacionDAO(view.context)
        val manager = LinearLayoutManager(context)
        moviAdapter = MovimientosAdapter(operDAO.obtenerMovimientosPorCuenta(cuentaUsuario.num_movil)) { user -> onItemSelected(user) } //ojito
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