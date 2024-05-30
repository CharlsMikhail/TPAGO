package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto


class ContactoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_nombre)

    fun render(item: Contacto, onClickListener:(Contacto) -> Unit) {
        viewNombre.text = item.nombres
        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}
