package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.CuentaAdministrador
import java.util.ArrayList

class CuentaAdministradorDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertarCuentaAdministrador(dniPersona: Int, email: String?, ipCuentaAdmin: String, contraseña: String, estadoActividad: Int): Long {
        val contentValues = ContentValues().apply {
            put("dni_persona", dniPersona)
            put("email", email)
            put("ip_cuenta_admin", ipCuentaAdmin)
            put("contraseña", contraseña)
            put("estado_de_actividad", estadoActividad)
        }
        return db.insert("cuenta_administrador", null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerCuentaAdministradorPorDni(dniPersona: Int): CuentaAdministrador? {
        val cursor: Cursor = db.query(
            "cuenta_administrador",
            null,
            "dni_persona = ?",
            arrayOf(dniPersona.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val cuentaAdmin = CuentaAdministrador(
                cursor.getInt(cursor.getColumnIndex("dni_persona")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("ip_cuenta_admin")),
                cursor.getString(cursor.getColumnIndex("contraseña")),
                cursor.getInt(cursor.getColumnIndex("estado_de_actividad"))
            )
            cursor.close()
            cuentaAdmin
        } else {
            cursor.close()
            null
        }
    }

    fun actualizarCuentaAdministrador(dniPersona: Int, nuevoEmail: String?, nuevaIpCuentaAdmin: String, nuevaContraseña: String, nuevoEstadoActividad: Int): Int {
        val contentValues = ContentValues().apply {
            put("email", nuevoEmail)
            put("ip_cuenta_admin", nuevaIpCuentaAdmin)
            put("contraseña", nuevaContraseña)
            put("estado_de_actividad", nuevoEstadoActividad)
        }
        return db.update(
            "cuenta_administrador",
            contentValues,
            "dni_persona = ?",
            arrayOf(dniPersona.toString())
        )
    }

    fun eliminarCuentaAdministrador(dniPersona: Int): Int {
        return db.delete("cuenta_administrador", "dni_persona = ?", arrayOf(dniPersona.toString()))
    }

    @SuppressLint("Range")
    fun obtenerTodasLasCuentasAdministrador(): List<CuentaAdministrador> {
        val cuentasAdmin: MutableList<CuentaAdministrador> = ArrayList()
        val cursor: Cursor = db.query("cuenta_administrador", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val cuentaAdmin = CuentaAdministrador(
                    cursor.getInt(cursor.getColumnIndex("dni_persona")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("ip_cuenta_admin")),
                    cursor.getString(cursor.getColumnIndex("contraseña")),
                    cursor.getInt(cursor.getColumnIndex("estado_de_actividad"))
                )
                cuentasAdmin.add(cuentaAdmin)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cuentasAdmin
    }
}
