package com.example.tpago2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.Contacto

class ContactoAdapter(private val isVisible: Boolean, private val items: MutableList<Contacto>, val onItemSelected: (Contacto) -> Unit): RecyclerView.Adapter<ContactoViewHolder>() {

    val contador = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto,parent, false)
        return ContactoViewHolder(isVisible, itemView)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val item = items[position]
        holder.render(item, onItemSelected)
    }

    fun addUser(usuario: Contacto) {
        items.add(0, usuario)
        notifyItemInserted(0)
    }

    private fun deleteUser(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }

    fun editUser(index: Int, usuario: Contacto) {
        items.removeAt(index)
        items[index] = usuario
        notifyItemChanged(index)
    }

}