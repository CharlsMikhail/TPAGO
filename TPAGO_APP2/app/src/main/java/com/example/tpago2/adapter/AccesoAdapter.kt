package com.example.tpago2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.entidades.AccesoAndChamba
import com.example.tpago2.gui.utilitarios.mostrarErrorDeConexion
import com.example.tpago2.gui.utilitarios.showCustomSnackBar
import com.example.tpago2.service.falla

class AccesoAdapter(private val items: MutableList<AccesoAndChamba>, private val idLayout: Int, val onItemSelected: (AccesoAndChamba) -> Unit): RecyclerView.Adapter<AccesoViewHolder>() {

    private lateinit var context2: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccesoViewHolder {
        context2 = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(idLayout, parent, false)
        return AccesoViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AccesoViewHolder, position: Int) {
        val item = items[position]
        holder.render(item, onItemSelected) {
            deleteTarjeta(position, item, holder.itemView)
        }
    }

    fun addTarjeta(tarjeta: AccesoAndChamba) {
        items.add(0, tarjeta)
        notifyItemInserted(0)
    }

    private fun deleteTarjeta(index: Int, item: AccesoAndChamba, itemView: View) {
        if (falla) {
            mostrarErrorDeConexion(context2)
            return
        }
        val builder = AlertDialog.Builder(context2)
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas eliminar a este número de tus accesos: ${item.nombres}?")
        builder.setPositiveButton("Sí") { _, _ ->
            // Acción a realizar si el usuario confirma la eliminación del acceso
            val daoTarjeta = AccesoEmpleadoDAO(context2)
            daoTarjeta.eliminarAccesoEmpleado(item.idAccesoOrChamba)

            items.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, items.size)
            //Toast.makeText(context2, "Acceso eliminado correctamente", Toast.LENGTH_SHORT).show()
            showCustomSnackBar(itemView,  "!Atención¡", "Acceso eliminado correctamente", 2)
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            // Acción a realizar si el usuario cancela la eliminación
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


    fun editTarjeta(index: Int, tarjeta: AccesoAndChamba) {
        items[index] = tarjeta
        notifyItemChanged(index)
    }
}
