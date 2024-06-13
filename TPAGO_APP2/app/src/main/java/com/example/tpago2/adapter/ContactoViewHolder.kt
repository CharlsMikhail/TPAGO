package com.example.tpago2.adapter

import android.content.res.Resources
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto


class ContactoViewHolder(isVisible: Boolean, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_name_user)
    private val viewCelular = itemView.findViewById<TextView>(R.id.txt_movil)
    private val btnAdd = itemView.findViewById<ImageButton>(R.id.agregar_acceso)
    init {
        if (isVisible) {
            btnAdd.visibility = View.VISIBLE
            itemView.isEnabled = false
        } else {
            btnAdd.visibility = View.GONE
        }
    }
    fun render(item: Contacto, onClickListener:(Contacto) -> Unit) {
        viewCelular.text = item.numMovil.toString()
        viewNombre.text = item.nombres
        if (!itemView.isEnabled) {
            val itemViewContactos = itemView.findViewById<ImageButton>(R.id.agregar_acceso)
            btnAdd.setOnClickListener {
                val drawable = itemViewContactos.drawable
                val agregarVerde = ResourcesCompat.getDrawable(itemView.resources, R.drawable.agregar_verde, null)
                val agregarGris = ResourcesCompat.getDrawable(itemView.resources, R.drawable.agregar_gris, null)

                if (drawable != null && agregarVerde != null && agregarGris != null) {
                    if (drawable.constantState == agregarVerde.constantState) {
                        itemViewContactos.setImageResource(R.drawable.agregar_gris)
                    } else {
                        itemViewContactos.setImageResource(R.drawable.agregar_verde)
                    }
                    onClickListener(item)
                }
            }
        }


        itemView.setOnClickListener {
            onClickListener(item)
        }

    }
}
