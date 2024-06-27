package com.example.tpago2.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.entidades.AccesoAndChamba
import com.example.tpago2.data.entidades.Contacto

class ContactoAdapter(private val userNumMovil: Int?, private val items: MutableList<Contacto>, val onItemSelected: (Contacto) -> Unit): RecyclerView.Adapter<ContactoViewHolder>() {

    private var listaOriginal: MutableList<Contacto> = items.toMutableList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto,parent, false)
        return ContactoViewHolder(userNumMovil, itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        Log.d("Position", "Ojo: $position")
        val item = items[position]
        holder.render(item, onItemSelected)
        //notifyDataSetChanged()
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

    private fun addViewListener() {

        }


    fun editUser(index: Int, usuario: Contacto) {
        items.removeAt(index)
        items[index] = usuario
        notifyItemChanged(index)
    }

    fun filtrado(txtBuscar: String?) {
        if (txtBuscar.isNullOrEmpty()) {
            items.clear()
            items.addAll(listaOriginal)
        } else {
            val listaFiltrada = items.filter {
                it.nombres.contains(txtBuscar, ignoreCase = true) || it.numMovil.toString().contains(txtBuscar, ignoreCase = true)
            }
            items.clear()
            items.addAll(listaFiltrada)
        }
        notifyDataSetChanged()
    }


}