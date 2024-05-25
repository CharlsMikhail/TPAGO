package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.Operacion
import java.util.ArrayList

class OperacionDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarOperacion(
        numCuentaOrigen: Int,
        numCuentaDestino: Int,
        horaOperacion: String,
        fechaOperacion: String,
        montoOperacion: Int
    ): Long {
        val contentValues = ContentValues().apply {
            put("num_cuenta_origen", numCuentaOrigen)
            put("num_cuenta_destino", numCuentaDestino)
            put("hora_operacion", horaOperacion)
            put("fecha_operacion", fechaOperacion)
            put("monto_operacion", montoOperacion)
        }
        return db.insert("operacion", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerOperacionesPorCuenta(numCuenta: String): List<Operacion> {
        val operaciones: MutableList<Operacion> = ArrayList()
        val cursor: Cursor = db.query(
            "operacion",
            null,
            "num_cuenta_origen = ? OR num_cuenta_destino = ?",
            arrayOf(numCuenta, numCuenta),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val operacion = Operacion(
                    cursor.getInt(cursor.getColumnIndex("id_operacion")),
                    cursor.getInt(cursor.getColumnIndex("num_cuenta_origen")),
                    cursor.getInt(cursor.getColumnIndex("num_cuenta_destino")),
                    cursor.getString(cursor.getColumnIndex("hora_operacion")),
                    cursor.getString(cursor.getColumnIndex("fecha_operacion")),
                    cursor.getInt(cursor.getColumnIndex("monto_operacion"))
                )
                operaciones.add(operacion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return operaciones
    }

    fun eliminarOperacion(idOperacion: Int): Int {
        return db.delete("operacion", "id_operacion = ?", arrayOf(idOperacion.toString()))
    }

    fun obtenerTotalOperaciones(): Int {
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM operacion", null)
        return if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            cursor.close()
            count
        } else {
            cursor.close()
            0
        }
    }

    @SuppressLint("Range")
    fun obtenerOperacionesPorOrigen(numCuentaOrigen: Int): List<Operacion> {
        val operaciones: MutableList<Operacion> = ArrayList()
        val cursor: Cursor = db.query(
            "operacion",
            null,
            "num_cuenta_origen = ?",
            arrayOf(numCuentaOrigen.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val operacion = Operacion(
                    cursor.getInt(cursor.getColumnIndex("id_operacion")),
                    cursor.getInt(cursor.getColumnIndex("num_cuenta_origen")),
                    cursor.getInt(cursor.getColumnIndex("num_cuenta_destino")),
                    cursor.getString(cursor.getColumnIndex("hora_operacion")),
                    cursor.getString(cursor.getColumnIndex("fecha_operacion")),
                    cursor.getInt(cursor.getColumnIndex("monto_operacion"))
                )
                operaciones.add(operacion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return operaciones
    }

}
