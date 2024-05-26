package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.dao.OperacionDAO
import com.example.tpago2.data.dao.PersonaDAO
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.Operacion


class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_nombre2)

    fun render(item: Operacion, l: OnClickListener) {
        val personDAO = PersonaDAO(itemView.context)

        personDAO.obtenerPersonaPorDni(item.num_cuenta_origen)

        viewNombre.text = item.nombres
    }


}
