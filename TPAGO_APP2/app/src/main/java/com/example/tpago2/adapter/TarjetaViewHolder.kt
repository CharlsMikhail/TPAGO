package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.TarjetaUsuario

class TarjetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNumTarjeta = itemView.findViewById<TextView>(R.id.txt_numero_tarjeta)
    private val viewFechVenTarjeta = itemView.findViewById<TextView>(R.id.txt_fecha_ven_tarjeta)
    private val btnEliminar = itemView.findViewById<ImageButton>(R.id.btn_eliminar_item_tarjeta)

    fun render(item: TarjetaUsuario, onClickListener: (TarjetaUsuario) -> Unit, l: OnClickListener) {
        viewNumTarjeta.text = item.num_tarjeta
        viewFechVenTarjeta.text = item.fecha_vencimiento
        btnEliminar.setOnClickListener(l)
        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}
