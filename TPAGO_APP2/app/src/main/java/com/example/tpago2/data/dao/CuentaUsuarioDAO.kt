package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.CuentaUsuario
import com.example.tpago2.data.entidades.CuentaDestino
import java.util.ArrayList

class CuentaUsuarioDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarCuentaUsuario(numMovil: Int, dniPersona: Int, saldo: Int, ipCuentaUsuario: String, contrasenia: Int, limitePorTransaccion: Int, email: String?, estadoActividad: Int): Long {
        val contentValues = ContentValues().apply {
            put("num_movil", numMovil)
            put("dni_persona", dniPersona)
            put("saldo", saldo)
            put("ip_cuenta_usuario", ipCuentaUsuario)
            put("contrasenia", contrasenia)
            put("limite_por_transaccion", limitePorTransaccion)
            put("email", email)
            put("estado_de_actividad", estadoActividad)
        }
        return db.insert("cuenta_usuario", null, contentValues)
    }

    fun obtenerUsuarioDestinoPorNumMovil(numMovil: Int): CuentaDestino? {
        val query = """
            SELECT p.primer_nombre || ' ' || p.ape_paterno || ' ' || p.ape_materno AS nombres, cu.num_movil
            FROM cuenta_usuario cu
            INNER JOIN persona p ON cu.dni_persona = p.dni_persona
            WHERE cu.num_movil = ?
        """
        val cursor: Cursor = db.rawQuery(query, arrayOf(numMovil.toString()))

        return if (cursor.moveToFirst()) {
            val cuentaDestino = CuentaDestino(
                nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres")),
                numMovil = cursor.getInt(cursor.getColumnIndexOrThrow("num_movil"))
            )
            cursor.close()
            cuentaDestino
        } else {
            cursor.close()
            null
        }
    }

    @SuppressLint("Range")
    fun obtenerCuentaUsuarioPorNumMovil(numMovil: Int): CuentaUsuario? {
        val cursor: Cursor = db.query(
            "cuenta_usuario",
            null,
            "num_movil = ?",
            arrayOf(numMovil.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val cuentaUsuario = CuentaUsuario(
                cursor.getInt(cursor.getColumnIndex("num_movil")),
                cursor.getInt(cursor.getColumnIndex("dni_persona")),
                cursor.getInt(cursor.getColumnIndex("saldo")),
                cursor.getString(cursor.getColumnIndex("ip_cuenta_usuario")),
                cursor.getInt(cursor.getColumnIndex("contrasenia")),
                cursor.getInt(cursor.getColumnIndex("limite_por_transaccion")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getInt(cursor.getColumnIndex("estado_de_actividad"))
            )
            cursor.close()
            cuentaUsuario
        } else {
            cursor.close()
            null
        }
    }


    fun eliminarCuentaUsuario(numMovil: String): Int {
        return db.delete("cuenta_usuario", "num_movil = ?", arrayOf(numMovil))
    }

    @SuppressLint("Range")
    fun obtenerTodasLasCuentasUsuarioExcepto(numMovilExcluir: Int): List<CuentaUsuario> {
        val cuentasUsuario: MutableList<CuentaUsuario> = ArrayList()
        val cursor: Cursor = db.query(
            "cuenta_usuario",
            null,
            "num_movil != ?",
            arrayOf(numMovilExcluir.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val cuentaUsuario = CuentaUsuario(
                    cursor.getInt(cursor.getColumnIndex("num_movil")),
                    cursor.getInt(cursor.getColumnIndex("dni_persona")),
                    cursor.getInt(cursor.getColumnIndex("saldo")),
                    cursor.getString(cursor.getColumnIndex("ip_cuenta_usuario")),
                    cursor.getInt(cursor.getColumnIndex("contrasenia")),
                    cursor.getInt(cursor.getColumnIndex("limite_por_transaccion")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getInt(cursor.getColumnIndex("estado_de_actividad"))
                )
                cuentasUsuario.add(cuentaUsuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cuentasUsuario
    }


    fun actualizarSaldo(numMovil: Int, monto: Int, incrementar: Boolean): Int {
        // Obtener el saldo actual del usuario
        val cursor = db.query(
            "cuenta_usuario",
            arrayOf("saldo"),
            "num_movil = ?",
            arrayOf(numMovil.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val saldoActual = cursor.getInt(cursor.getColumnIndexOrThrow("saldo"))
            cursor.close()

            // Calcular el nuevo saldo
            val nuevoSaldo = if (incrementar) {
                saldoActual + monto
            } else {
                saldoActual - monto
            }

            // Actualizar el saldo en la base de datos
            val contentValues = ContentValues().apply {
                put("saldo", nuevoSaldo)
            }
            return db.update(
                "cuenta_usuario",
                contentValues,
                "num_movil = ?",
                arrayOf(numMovil.toString())
            )
        } else {
            cursor.close()
            return 0 // No se encontró la cuenta
        }
    }


    @SuppressLint("Range")
    fun obtenerSaldo(numMovil: Int): Int {
        val cursor: Cursor = db.query(
            "cuenta_usuario",
            arrayOf("saldo"),
            "num_movil = ?",
            arrayOf(numMovil.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val saldo = cursor.getInt(cursor.getColumnIndex("saldo"))
            cursor.close()
            saldo
        } else {
            cursor.close()
            0
        }
    }
}
