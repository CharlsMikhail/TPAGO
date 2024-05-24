package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Usuario

class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_nombre)
    private val viewEdad = itemView.findViewById<TextView>(R.id.txt_edad)

    fun render(item: Usuario, l: OnClickListener) {
        viewNombre.text = item.nombre
        viewEdad.text = item.edad.toString()
    }


}
