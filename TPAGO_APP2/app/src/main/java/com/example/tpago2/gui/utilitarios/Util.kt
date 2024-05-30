package com.example.tpago2.gui.utilitarios

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.tpago2.R
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