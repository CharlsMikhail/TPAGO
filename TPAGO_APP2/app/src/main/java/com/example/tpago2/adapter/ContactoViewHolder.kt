package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto


class ContactoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_name_user)
    private val viewCelular = itemView.findViewById<TextView>(R.id.txt_movil)

    fun render(item: Contacto, onClickListener:(Contacto) -> Unit) {
        viewCelular.text = item.numMovil.toString()
        viewNombre.text = item.nombres
        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}
