package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.CuentaUsuario
import java.util.ArrayList

class CuentaUsuarioDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarCuentaUsuario(numMovil: String, dniPersona: Int, saldo: Int, ipCuentaUsuario: String, contraseña: String, limitePorTransaccion: Int, email: String?, estadoActividad: Int): Long {
        val contentValues = ContentValues().apply {
            put("num_movil", numMovil)
            put("dni_persona", dniPersona)
            put("saldo", saldo)
            put("ip_cuenta_usuario", ipCuentaUsuario)
            put("contraseña", contraseña)
            put("limite_por_transaccion", limitePorTransaccion)
            put("email", email)
            put("estado_de_actividad", estadoActividad)
        }
        return db.insert("cuenta_usuario", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerCuentaUsuarioPorNumMovil(numMovil: String): CuentaUsuario? {
        val cursor: Cursor = db.query(
            "cuenta_usuario",
            null,
            "num_movil = ?",
            arrayOf(numMovil),
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
                cursor.getInt(cursor.getColumnIndex("contraseña")),
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

    fun actualizarCuentaUsuario(numMovil: String, nuevoDniPersona: Int, nuevoSaldo: Int, nuevaIpCuentaUsuario: String, nuevaContraseña: String, nuevoLimitePorTransaccion: Int, nuevoEmail: String?, nuevoEstadoActividad: Int): Int {
        val contentValues = ContentValues().apply {
            put("dni_persona", nuevoDniPersona)
            put("saldo", nuevoSaldo)
            put("ip_cuenta_usuario", nuevaIpCuentaUsuario)
            put("contraseña", nuevaContraseña)
            put("limite_por_transaccion", nuevoLimitePorTransaccion)
            put("email", nuevoEmail)
            put("estado_de_actividad", nuevoEstadoActividad)
        }
        return db.update(
            "cuenta_usuario",
            contentValues,
            "num_movil = ?",
            arrayOf(numMovil)
        )
    }

    fun eliminarCuentaUsuario(numMovil: String): Int {
        return db.delete("cuenta_usuario", "num_movil = ?", arrayOf(numMovil))
    }

    @SuppressLint("Range")
    fun obtenerTodasLasCuentasUsuario(): List<CuentaUsuario> {
        val cuentasUsuario: MutableList<CuentaUsuario> = ArrayList()
        val cursor: Cursor = db.query("cuenta_usuario", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val cuentaUsuario = CuentaUsuario(
                    cursor.getInt(cursor.getColumnIndex("num_movil")),
                    cursor.getInt(cursor.getColumnIndex("dni_persona")),
                    cursor.getInt(cursor.getColumnIndex("saldo")),
                    cursor.getString(cursor.getColumnIndex("ip_cuenta_usuario")),
                    cursor.getInt(cursor.getColumnIndex("contraseña")),
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

    fun actualizarSaldo(numMovil: String, nuevoSaldo: Int): Int {
        val contentValues = ContentValues().apply {
            put("saldo", nuevoSaldo)
        }
        return db.update(
            "cuenta_usuario",
            contentValues,
            "num_movil = ?",
            arrayOf(numMovil)
        )
    }

    @SuppressLint("Range")
    fun obtenerSaldo(numMovil: String): Int {
        val cursor: Cursor = db.query(
            "cuenta_usuario",
            arrayOf("saldo"),
            "num_movil = ?",
            arrayOf(numMovil),
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
