package com.example.tpago2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.service.Usuario

class UsuariosAdapter(val items: MutableList<Usuario>): RecyclerView.Adapter<UsuarioViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto,parent, false)
        return UsuarioViewHolder(itemView)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val item = items[position]
        holder.render(item) {
            deleteUser(position)
        }
    }

    fun addUser(usuario: Usuario) {
        items.add(0, usuario)
        notifyItemInserted(0)
    }

    fun deleteUser(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }

    fun editUser(index: Int, usuario: Usuario) {
        items.removeAt(index)
        items[index] = usuario
        notifyItemChanged(index)
    }

}