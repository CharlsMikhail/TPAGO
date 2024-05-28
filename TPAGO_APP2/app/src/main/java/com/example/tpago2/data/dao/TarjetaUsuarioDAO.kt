package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.TarjetaUsuario

class TarjetaUsuarioDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarTarjetaUsuario(
        numTarjeta: String,
        numMovilUsuario: Int,
        fechaVencimiento: String,
        codigoCsv: String
    ): Long {
        val contentValues = ContentValues().apply {
            put("num_tarjeta", numTarjeta)
            put("num_movil_usuario", numMovilUsuario)
            put("fecha_vencimiento", fechaVencimiento)
            put("codigo_csv", codigoCsv)
        }
        return db.insert("tarjeta_usuario", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerTarjetasUsuarioPorNumeroMovil(numMovilUsuario: Int): MutableList<TarjetaUsuario> {
        val tarjetasUsuario: MutableList<TarjetaUsuario> = ArrayList()
        val cursor: Cursor = db.query(
            "tarjeta_usuario",
            null,
            "num_movil = ?",
            arrayOf(numMovilUsuario.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val tarjetaUsuario = TarjetaUsuario(
                    cursor.getString(cursor.getColumnIndex("num_tarjeta")),
                    cursor.getInt(cursor.getColumnIndex("num_movil")),
                    cursor.getString(cursor.getColumnIndex("fecha_vencimiento")),
                    cursor.getString(cursor.getColumnIndex("codigo_csv"))
                )
                tarjetasUsuario.add(tarjetaUsuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tarjetasUsuario
    }

    fun eliminarTarjetaUsuario(numTarjeta: String): Int {
        return db.delete("tarjeta_usuario", "num_tarjeta = ?", arrayOf(numTarjeta))
    }

    fun obtenerTotalTarjetasUsuario(): Int {
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM tarjeta_usuario", null)
        return if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            cursor.close()
            count
        } else {
            cursor.close()
            0
        }
    }

    fun obtenerTotalTarjetasPorUsuario(numMovilUsuario: Int): Int {
        val cursor: Cursor = db.rawQuery(
            "SELECT COUNT(*) FROM tarjeta_usuario WHERE num_movil_usuario = ?",
            arrayOf(numMovilUsuario.toString())
        )
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
