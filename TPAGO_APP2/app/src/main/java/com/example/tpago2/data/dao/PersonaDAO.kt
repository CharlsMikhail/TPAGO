package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.Persona
import java.util.ArrayList

class PersonaDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarPersona(dniPersona: Int, primerNombre: String, segundoNombre: String?, apePaterno: String, apeMaterno: String): Long {
        val contentValues = ContentValues().apply {
            put("dni_persona", dniPersona)
            put("primer_nombre", primerNombre)
            put("segundo_nombre", segundoNombre)
            put("ape_paterno", apePaterno)
            put("ape_materno", apeMaterno)
        }
        return db.insert("persona", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerPersonaPorDni(dniPersona: Int): Persona? {
        val cursor: Cursor = db.query(
            "persona",
            null,
            "dni_persona = ?",
            arrayOf(dniPersona.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val persona = Persona(
                cursor.getInt(cursor.getColumnIndex("dni_persona")),
                cursor.getString(cursor.getColumnIndex("primer_nombre")),
                cursor.getString(cursor.getColumnIndex("segundo_nombre")),
                cursor.getString(cursor.getColumnIndex("ape_paterno")),
                cursor.getString(cursor.getColumnIndex("ape_materno"))
            )
            cursor.close()
            persona
        } else {
            cursor.close()
            null
        }
    }

    fun actualizarPersona(dniPersona: Int, nuevoPrimerNombre: String, nuevoSegundoNombre: String?, nuevoApePaterno: String, nuevoApeMaterno: String): Int {
        val contentValues = ContentValues().apply {
            put("primer_nombre", nuevoPrimerNombre)
            put("segundo_nombre", nuevoSegundoNombre)
            put("ape_paterno", nuevoApePaterno)
            put("ape_materno", nuevoApeMaterno)
        }
        return db.update(
            "persona",
            contentValues,
            "dni_persona = ?",
            arrayOf(dniPersona.toString())
        )
    }

    fun eliminarPersona(dniPersona: Int): Int {
        return db.delete("persona", "dni_persona = ?", arrayOf(dniPersona.toString()))
    }

    @SuppressLint("Range")
    fun obtenerTodasLasPersonas(): List<Persona> {
        val personas: MutableList<Persona> = ArrayList()
        val cursor: Cursor = db.query("persona", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val persona = Persona(
                    cursor.getInt(cursor.getColumnIndex("dni_persona")),
                    cursor.getString(cursor.getColumnIndex("primer_nombre")),
                    cursor.getString(cursor.getColumnIndex("segundo_nombre")),
                    cursor.getString(cursor.getColumnIndex("ape_paterno")),
                    cursor.getString(cursor.getColumnIndex("ape_materno"))
                )
                personas.add(persona)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return personas
    }
}
