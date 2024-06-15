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

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_name_user)
    private val viewFecha = itemView.findViewById<TextView>(R.id.date_and_hour)
    private val viewMonto = itemView.findViewById<TextView>(R.id.monto_transferencia)
    @SuppressLint("SetTextI18n")
    fun render(item: Movimiento, onClickListener:(Movimiento) -> Unit) {
        viewNombre.text = item.nombre + " " +  item.apePaterno + " " + item.apeMaterno
        viewFecha.text = item.fecha +  ", " + item.hora
        if (item.tipo != "suma") {
            viewMonto.text = "- S/" + item.monto
            val hsv = floatArrayOf(0f, 0.7f, 0.8f) // Tonos más oscuros tienen menor valor de S y V

            val colorRojoOscuro = Color.HSVToColor(hsv)
            viewMonto.setTextColor(colorRojoOscuro)
        } else {
            viewMonto.text = "+ S/" + item.monto
            val hsv = floatArrayOf(140f, 0.6f, 0.8f) // Morado verdoso con saturación y valor moderados

            val colorMoradoVerdoso = Color.HSVToColor(hsv)
            viewMonto.setTextColor(colorMoradoVerdoso)
        }

        if (viewNombre.text.toString().length >= 20) {
            viewNombre.textSize = 18f
        }

        itemView.setOnClickListener() {
            onClickListener(item)
        }
    }
}
