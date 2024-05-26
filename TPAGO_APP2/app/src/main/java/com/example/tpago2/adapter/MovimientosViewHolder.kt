package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.Operacion


class MovimientosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_nombre)

    fun render(item: Operacion, l: OnClickListener) {
        viewNombre.text = item.num_cuenta_destino
    }


}
