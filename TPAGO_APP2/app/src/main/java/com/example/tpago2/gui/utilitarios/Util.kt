package com.example.tpago2.gui.utilitarios

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.tpago2.R
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import kotlin.system.exitProcess


fun mostrarErrorDeConexion(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Error de Conexión")
    builder.setMessage("Ocurrio un error. Por favor, verifica tu conexión a Internet e inténtalo de nuevo.")
    builder.setPositiveButton("Aceptar") { dialog, _ ->
        exitProcess(1)
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

@SuppressLint("RestrictedApi")
fun showCustomSnackBar(view: View, header: String, message: String, gravity: Int = 3) {
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)

    // Inflar el diseño personalizado para el SnackBar
    val inflater = LayoutInflater.from(view.context)
    val customSnackView: View = inflater.inflate(R.layout.custom_snackbar, null)

    // Configurar los textos del encabezado y el mensaje
    val headerTextView: TextView = customSnackView.findViewById(R.id.snackbar_text_header)
    val messageTextView: TextView = customSnackView.findViewById(R.id.snackbar_text_message)
    headerTextView.text = header
    messageTextView.text = message

    // Configurar el color de fondo basado en la gravedad
    val (backgroundColor, textColor) = when (gravity) {
        1 -> Pair("#FF0000", "#FFFFFF") // Rojo
        2 -> Pair("#099e0c", "#FFFFFF") // Verde
        3 -> Pair("#FFC107", "#000000") // Amarillo
        else -> Pair("#FFC107", "#000000") // Valor predeterminado
    }
    customSnackView.setBackgroundColor(Color.parseColor(backgroundColor))
    headerTextView.setTextColor(Color.parseColor(textColor))
    messageTextView.setTextColor(Color.parseColor(textColor))

    // Añadir la vista personalizada al SnackBar
    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackbarLayout.setPadding(0, 0, 0, 5) // Eliminar el padding
    snackbarLayout.addView(customSnackView, 0)

    snackbar.show()
}