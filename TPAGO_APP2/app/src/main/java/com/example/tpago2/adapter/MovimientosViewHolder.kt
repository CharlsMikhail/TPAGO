package com.example.tpago2.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.Movimiento
import com.example.tpago2.data.entidades.Operacion


class MovimientosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_nombre2)
    private val viewFecha = itemView.findViewById<TextView>(R.id.txt_fecha_y_hora2)
    private val viewMonto = itemView.findViewById<TextView>(R.id.txt_monto2)
    @SuppressLint("SetTextI18n")
    fun render(item: Movimiento, onClickListener:(Movimiento) -> Unit) {
        viewNombre.text = item.nombre + " " +  item.apePaterno
        viewFecha.text = item.fecha +  ", " + item.hora
        if (item.tipo == "suma") {
            viewMonto.text = "+ S/" + item.monto
            viewMonto.setTextColor(Color.GREEN)
        } else {
            viewMonto.text = "- S/" + item.monto
            viewMonto.setTextColor(Color.RED)
        }

        itemView.setOnClickListener() {
            onClickListener(item)
        }
    }
}
