package com.example.tpago.gestionTarjetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tpago.R

/**
 * A simple [Fragment] subclass.
 * Use the [SeleccionarTarjetaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeleccionarTarjetaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(com.example.tpago.ARG_PARAM1)
            param2 = it.getString(com.example.tpago.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccionar_tarjeta, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeleccionarTarjetaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeleccionarTarjetaFragment().apply {
                arguments = Bundle().apply {
                    putString(com.example.tpago.ARG_PARAM1, param1)
                    putString(com.example.tpago.ARG_PARAM2, param2)
                }
            }
    }
}