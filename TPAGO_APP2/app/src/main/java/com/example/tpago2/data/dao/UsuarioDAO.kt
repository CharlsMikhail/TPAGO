package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.Usuario
import java.util.ArrayList

class UsuarioDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarUsuario(dniPersona: Int, fechaInicio: String, observaciones: String?): Long {
        val contentValues = ContentValues().apply {
            put("dni_persona", dniPersona)
            put("fecha_inicio", fechaInicio)
            put("observaciones", observaciones)
        }
        return db.insert("usuario", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerUsuarioPorDni(dniPersona: Int): Usuario? {
        val cursor: Cursor = db.query(
            "usuario",
            null,
            "dni_persona = ?",
            arrayOf(dniPersona.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val usuario = Usuario(
                cursor.getInt(cursor.getColumnIndex("dni_persona")),
                cursor.getString(cursor.getColumnIndex("fecha_inicio")),
                cursor.getString(cursor.getColumnIndex("observaciones"))
            )
            cursor.close()
            usuario
        } else {
            cursor.close()
            null
        }
    }

    fun actualizarUsuario(dniPersona: Int, nuevaFechaInicio: String, nuevasObservaciones: String?): Int {
        val contentValues = ContentValues().apply {
            put("fecha_inicio", nuevaFechaInicio)
            put("observaciones", nuevasObservaciones)
        }
        return db.update(
            "usuario",
            contentValues,
            "dni_persona = ?",
            arrayOf(dniPersona.toString())
        )
    }

    fun eliminarUsuario(dniPersona: Int): Int {
        return db.delete("usuario", "dni_persona = ?", arrayOf(dniPersona.toString()))
    }

    @SuppressLint("Range")
    fun obtenerTodosLosUsuarios(): List<Usuario> {
        val usuarios: MutableList<Usuario> = ArrayList()
        val cursor: Cursor = db.query("usuario", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val usuario = Usuario(
                    cursor.getInt(cursor.getColumnIndex("dni_persona")),
                    cursor.getString(cursor.getColumnIndex("fecha_inicio")),
                    cursor.getString(cursor.getColumnIndex("observaciones"))
                )
                usuarios.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return usuarios
    }
}
