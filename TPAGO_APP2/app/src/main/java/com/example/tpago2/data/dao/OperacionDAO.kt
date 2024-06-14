package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.Movimiento
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
    fun obtenerOperacionesPorOrigen(numCuentaOrigen: Int): MutableList<Operacion> {
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

    @SuppressLint("Range")
    fun obtenerMovimientosPorCuenta(numCuenta: Int): MutableList<Movimiento> {
        val movimientos: MutableList<Movimiento> = ArrayList()
        val query = """
            SELECT o.monto_operacion, o.fecha_operacion, o.hora_operacion, 
                   p1.primer_nombre AS nombre_origen, p1.ape_paterno AS apellido_origen, p1.ape_materno AS apellido_origen2, cu1.num_movil AS movil_origen,
                   p2.primer_nombre AS nombre_destino, p2.ape_paterno AS apellido_destino,p2.ape_materno AS apellido_destino2, cu2.num_movil AS movil_destino
            FROM operacion o
            INNER JOIN cuenta_usuario cu1 ON o.num_cuenta_origen = cu1.num_movil
            INNER JOIN persona p1 ON cu1.dni_persona = p1.dni_persona
            INNER JOIN cuenta_usuario cu2 ON o.num_cuenta_destino = cu2.num_movil
            INNER JOIN persona p2 ON cu2.dni_persona = p2.dni_persona
            WHERE o.num_cuenta_origen = ? OR o.num_cuenta_destino = ?
            ORDER BY o.fecha_operacion DESC, o.hora_operacion DESC
            LIMIT 20
        """
        val cursor: Cursor = db.rawQuery(query, arrayOf(numCuenta.toString(), numCuenta.toString()))
        if (cursor.moveToFirst()) {
            do {
                val montoOperacion = cursor.getInt(cursor.getColumnIndex("monto_operacion"))
                val fechaOperacion = cursor.getString(cursor.getColumnIndex("fecha_operacion"))
                val horaOperacion = cursor.getString(cursor.getColumnIndex("hora_operacion"))

                val nombre: String
                val apellido: String
                val apellido2: String
                val movil: Int
                val tipo: String

                if (cursor.getInt(cursor.getColumnIndex("movil_origen")) == numCuenta) {
                    // La cuenta de origen es la cuenta proporcionada
                    nombre = cursor.getString(cursor.getColumnIndex("nombre_destino"))
                    apellido = cursor.getString(cursor.getColumnIndex("apellido_destino"))
                    apellido2 = cursor.getString(cursor.getColumnIndex("apellido_destino2"))
                    movil = cursor.getInt(cursor.getColumnIndex("movil_destino"))
                    tipo = "resta"
                } else {
                    // La cuenta de destino es la cuenta proporcionada
                    nombre = cursor.getString(cursor.getColumnIndex("nombre_origen"))
                    apellido = cursor.getString(cursor.getColumnIndex("apellido_origen"))
                    apellido2 = cursor.getString(cursor.getColumnIndex("apellido_origen2"))
                    movil = cursor.getInt(cursor.getColumnIndex("movil_origen"))
                    tipo = "suma"
                }

                val movimiento = Movimiento(
                    nombre = nombre,
                    apePaterno = apellido,
                    apeMaterno = apellido2,
                    movil = movil,
                    monto = montoOperacion,
                    fecha = fechaOperacion,
                    hora = horaOperacion,
                    tipo = tipo
                )
                movimientos.add(movimiento)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return movimientos
    }

}
