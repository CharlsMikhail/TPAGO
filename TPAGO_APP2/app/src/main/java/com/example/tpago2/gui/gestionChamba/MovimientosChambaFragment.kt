package com.example.tpago2.gui.gestionChamba
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tpago2.R

class MovimientosChambaFragment : Fragment(R.layout.fragment_movimientos_chamba) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actualizarInterfaz(view)
        eventos(view)


    }

    private fun eventos(view: View) {

    }

    private fun actualizarInterfaz(view: View) {
    }


}