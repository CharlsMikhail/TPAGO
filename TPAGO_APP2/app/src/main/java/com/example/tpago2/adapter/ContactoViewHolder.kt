package com.example.tpago2.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tpago2.R
import com.example.tpago2.data.dao.AccesoEmpleadoDAO
import com.example.tpago2.data.dao.CuentaUsuarioDAO
import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.service.isAdd
import com.example.tpago2.service.restanteAccesos

class ContactoViewHolder(private val userNumMovil: Int?, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_name_user)
    private val viewCelular = itemView.findViewById<TextView>(R.id.txt_movil)
    private val btnAdd = itemView.findViewById<ImageButton>(R.id.agregar_acceso)

    init {
        if (userNumMovil != null) btnAdd.visibility = View.VISIBLE
    }

    fun render(
        item: Contacto,
        onClickListener: (Contacto) -> Unit
    ) {
        viewCelular.text = item.numMovil.toString()
        viewNombre.text = item.nombres

        // Obtener drawables
        val agregarVerde = ResourcesCompat.getDrawable(itemView.resources, R.drawable.agregar_verde, null)
        val agregarGris = ResourcesCompat.getDrawable(itemView.resources, R.drawable.agregar_gris, null)

        if (userNumMovil != null) {
            // Restablecer el estado de btnAdd basado en el estado del elemento
            if (item.isAdd) {
                btnAdd.setImageDrawable(agregarVerde)
            } else {
                btnAdd.setImageDrawable(agregarGris)
            }

            // Eliminar cualquier escuchador anterior
            btnAdd.setOnClickListener(null)

            // Configurar el nuevo escuchador
            btnAdd.setOnClickListener {
                val drawable = btnAdd.drawable

                if (drawable != null && agregarVerde != null && agregarGris != null) {
                    if (drawable.constantState == agregarVerde.constantState) {
                        btnAdd.setImageDrawable(agregarGris)
                        item.isAdd = false
                        restanteAccesos++
                        Toast.makeText(itemView.context, "Restante: ${restanteAccesos}", Toast.LENGTH_SHORT).show()

                    } else {
                        if (restanteAccesos == 0) {
                            Toast.makeText(itemView.context, "Límite de accesos superado", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        val daoCueUsu = CuentaUsuarioDAO(itemView.context)
                        val daoAcceso = AccesoEmpleadoDAO(itemView.context)

                        if (item.numMovil == userNumMovil) {
                            Toast.makeText(itemView.context, "No puede darse acceso a sí mismo", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        } else if (daoAcceso.verificarEmpleadoDelEmpleador(userNumMovil, item.numMovil)) {
                            Toast.makeText(itemView.context, "El contacto ${item.nombres} ya forma parte de tus empleados", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        } else if (daoCueUsu.obtenerUsuarioDestinoPorNumMovil(item.numMovil) == null) {
                            Toast.makeText(itemView.context, "El contacto ${item.nombres} no pertenece a TPAGO", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        item.isAdd = true
                        restanteAccesos--
                        Toast.makeText(itemView.context, "Restante: ${restanteAccesos}", Toast.LENGTH_SHORT).show()
                        btnAdd.setImageDrawable(agregarVerde)
                    }
                    onClickListener(item)
                }
            }
        } else {
            itemView.setOnClickListener {
                onClickListener(item)
            }
        }
    }
}
