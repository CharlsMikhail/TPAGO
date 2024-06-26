package com.example.tpago2.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException

class CargaMasiva(private val context: Context) {

    private var dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)

    fun realizarCargaMasiva() {
        val db = dbHelper.writableDatabase

        db.beginTransaction()
        try {
            cargarDatosDesdeCSV(db, "csv_files_wos/persona.csv", TABLE_PERSONA_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/cuenta_administrador.csv", TABLE_CUENTA_ADMINISTRADOR_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/usuario.csv", TABLE_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/cuenta_usuario.csv", TABLE_CUENTA_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/operacion.csv", TABLE_OPERACION_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/tarjeta_usuario.csv", TABLE_TARJETA_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/recarga.csv", TABLE_RECARGA_INSERT)
            cargarDatosDesdeCSV(db, "csv_files_wos/acceso_empleado.csv", TABLE_ACCESO_EMPLEADO_INSERT)

            db.setTransactionSuccessful()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
    }

    private fun cargarDatosDesdeCSV(db: SQLiteDatabase, fileName: String, insertStatement: String) {
        val inputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val values = line!!.split(",")
            Log.d("jajaja", "Valores obtenidos: $values") // Agrega esta línea
            val statement = db.compileStatement(insertStatement)
            for ((index, value) in values.withIndex()) {
                statement.bindString(index + 1, value)
            }
            statement.executeInsert()
            statement.clearBindings()
        }
        reader.close()
    }

    companion object {
        const val TABLE_PERSONA_INSERT = "INSERT INTO persona (dni_persona, primer_nombre, segundo_nombre, ape_paterno, ape_materno) VALUES (?, ?, ?, ?, ?)"
        const val TABLE_CUENTA_ADMINISTRADOR_INSERT = "INSERT INTO cuenta_administrador (dni_persona, email, ip_cuenta_admin, contrasenia, estado_de_actividad) VALUES (?, ?, ?, ?, ?)"
        const val TABLE_USUARIO_INSERT = "INSERT INTO usuario (dni_persona, fecha_inicio, observaciones) VALUES (?, ?, ?)"
        const val TABLE_CUENTA_USUARIO_INSERT = "INSERT INTO cuenta_usuario (num_movil, dni_persona, saldo, ip_cuenta_usuario, contrasenia, limite_por_transaccion, email, estado_de_actividad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        const val TABLE_OPERACION_INSERT = "INSERT INTO operacion (id_operacion, num_cuenta_origen, num_cuenta_destino, hora_operacion, fecha_operacion, monto_operacion) VALUES (?, ?, ?, ?, ?, ?)"
        const val TABLE_TARJETA_USUARIO_INSERT = "INSERT INTO tarjeta_usuario (num_tarjeta, num_movil, fecha_vencimiento, codigo_csv) VALUES (?, ?, ?, ?)"
        const val TABLE_RECARGA_INSERT = "INSERT INTO recarga (id_recarga, tipo, num_tarjeta, codigo_generado, monto, fecha, hora) VALUES (?, ?, ?, ?, ?, ?, ?)"
        const val TABLE_ACCESO_EMPLEADO_INSERT = "INSERT INTO acceso_empleado (id_acceso, num_movil_empleador, num_movil_empleado, estado_acceso, fecha_acceso) VALUES (?, ?, ?, ?, ?)"
    }
}
