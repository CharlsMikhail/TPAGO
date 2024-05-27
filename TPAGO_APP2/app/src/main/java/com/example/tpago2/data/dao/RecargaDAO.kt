package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.Recarga

class RecargaDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarRecarga(
        tipo: String,
        numTarjeta: String?,
        codigoGenerado: String?,
        monto: Int,
        fecha: String,
        hora: String
    ): Long {
        val contentValues = ContentValues().apply {
            put("tipo", tipo)
            put("num_tarjeta", numTarjeta)
            put("codigo_generado", codigoGenerado)
            put("monto", monto)
            put("fecha", fecha)
            put("hora", hora)
        }
        return db.insert("recarga", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerRecargasPorTipo(tipo: String): List<Recarga> {
        val recargas: MutableList<Recarga> = mutableListOf()
        val cursor: Cursor = db.query(
            "recarga",
            null,
            "tipo = ?",
            arrayOf(tipo),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val recarga = Recarga(
                    cursor.getInt(cursor.getColumnIndex("id_recarga")),
                    cursor.getString(cursor.getColumnIndex("tipo")),
                    cursor.getString(cursor.getColumnIndex("num_tarjeta")),
                    cursor.getString(cursor.getColumnIndex("codigo_generado")),
                    cursor.getInt(cursor.getColumnIndex("monto")),
                    cursor.getString(cursor.getColumnIndex("fecha")),
                    cursor.getString(cursor.getColumnIndex("hora"))
                )
                recargas.add(recarga)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return recargas
    }

    fun eliminarRecarga(idRecarga: Int): Int {
        return db.delete("recarga", "id_recarga = ?", arrayOf(idRecarga.toString()))
    }

    fun obtenerTotalRecargas(): Int {
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM recarga", null)
        return if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            cursor.close()
            count
        } else {
            cursor.close()
            0
        }
    }
}
