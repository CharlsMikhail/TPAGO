package com.example.tpago2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.Movimiento
import com.example.tpago2.data.entidades.Operacion

class MovimientosAdapter(val items: MutableList<Movimiento>, val onItemSelected: (Movimiento) -> Unit): RecyclerView.Adapter<MovimientosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movimiento,parent, false)
        return MovimientosViewHolder(itemView)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovimientosViewHolder, position: Int) {
        val item = items[position]
        holder.render(item, onItemSelected)
    }

    fun addUser(usuario: Movimiento) {
        items.add(0, usuario)
        notifyItemInserted(0)
    }

    private fun deleteUser(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }

    fun editUser(index: Int, usuario: Movimiento) {
        items.removeAt(index)
        items[index] = usuario
        notifyItemChanged(index)
    }

}