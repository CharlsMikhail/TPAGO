package com.example.tpago2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.data.dao.TarjetaUsuarioDAO
import com.example.tpago2.data.entidades.TarjetaUsuario
import com.example.tpago2.gui.utilitarios.mostrarErrorDeConexion
import com.example.tpago2.gui.utilitarios.showCustomSnackBar
import com.example.tpago2.service.falla

class TarjetaAdapter(private val items: MutableList<TarjetaUsuario>, private val idLayout: Int, val onItemSelected: (TarjetaUsuario) -> Unit): RecyclerView.Adapter<TarjetaViewHolder>() {

    lateinit private var context2: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarjetaViewHolder {
        context2 = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(idLayout, parent, false)
        return TarjetaViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TarjetaViewHolder, position: Int) {
        val item = items[position]
        holder.render(item, onItemSelected) {
            deleteTarjeta(position, item, holder.itemView)
        }
    }

    fun addTarjeta(tarjeta: TarjetaUsuario) {
        items.add(0, tarjeta)
        notifyItemInserted(0)
    }

    private fun deleteTarjeta(index: Int, item: TarjetaUsuario, itemView: View) {
        if (falla) {
            mostrarErrorDeConexion(context2)
            return
        }
        val builder = AlertDialog.Builder(context2)
        builder.setTitle("Confirmación")
        val maskedNumTarjeta = item.num_tarjeta.takeLast(4).padStart(item.num_tarjeta.length, '*')
        val formattedMaskedNumTarjeta = maskedNumTarjeta.chunked(4).joinToString(" ")
        val customTarjet = formattedMaskedNumTarjeta
        builder.setMessage("¿Estás seguro de que deseas eliminar esta tarjeta: ${customTarjet}?")
        builder.setPositiveButton("Sí") { _, _ ->
            // Acción a realizar si el usuario confirma la eliminación
            val daoTarjeta = TarjetaUsuarioDAO(context2)
            daoTarjeta.eliminarTarjetaUsuario(item.num_tarjeta)

            items.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, items.size)
            //Toast.makeText(context2, "Tarjeta eliminada correctamente", Toast.LENGTH_SHORT).show()
            showCustomSnackBar(itemView, "¡Exito!", "Tarjeta eliminada correctamente", 2)
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            // Acción a realizar si el usuario cancela la eliminación
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


    fun editTarjeta(index: Int, tarjeta: TarjetaUsuario) {
        items[index] = tarjeta
        notifyItemChanged(index)
    }
}
