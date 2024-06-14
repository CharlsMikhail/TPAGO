package com.example.tpago2.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.AccesoAndChamba
import com.example.tpago2.data.entidades.TarjetaUsuario

class AccesoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNumTarjeta = itemView.findViewById<TextView>(R.id.txt_numero_tarjeta)
    private val viewFechVenTarjeta = itemView.findViewById<TextView>(R.id.txt_fecha_ven_tarjeta)
    private val btnEliminar = itemView.findViewById<ImageButton>(R.id.btn_eliminar_item_tarjeta)
    private val imgAcceso = itemView.findViewById<ImageView>(R.id.img_usuario)

    fun render(item: AccesoAndChamba, onClickListener: (AccesoAndChamba) -> Unit, l: OnClickListener) {
        viewNumTarjeta.text = item.nombres
        imgAcceso.setImageResource(R.drawable.verified_user)
        viewFechVenTarjeta.text = item.numMovil.toString()
        btnEliminar.setOnClickListener(l)
        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}
