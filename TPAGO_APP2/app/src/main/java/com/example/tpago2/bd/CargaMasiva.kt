package com.example.tpago2.bd

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import android.database.sqlite.SQLiteDatabase
import java.io.IOException

class CargaMasiva(private val context: Context) {

    private var dbHelper: DataBaseHelper = DataBaseHelper(context)

    fun realizarCargaMasiva(): DataBaseHelper{
        val db = dbHelper.writableDatabase
        //db.execSQL("INSERT INTO persona (dni_persona, primer_nombre, segundo_nombre, ape_paterno, ape_materno) VALUES ('72864753', 'John', 'Doe', 'Smith', 'Johnson')")

        db.beginTransaction()
        try {
            cargarDatosDesdeCSV(db, "csv_files/persona.csv", TABLE_PERSONA_INSERT)
            /*cargarDatosDesdeCSV(db, "administrador.csv", TABLE_ADMINISTRADOR_INSERT)
            cargarDatosDesdeCSV(db, "cliente.csv", TABLE_CLIENTE_INSERT)
            cargarDatosDesdeCSV(db, "usuario.csv", TABLE_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "cuenta_usuario.csv", TABLE_CUENTA_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "departamento.csv", TABLE_DEPARTAMENTO_INSERT)
            cargarDatosDesdeCSV(db, "provincia.csv", TABLE_PROVINCIA_INSERT)
            cargarDatosDesdeCSV(db, "distrito.csv", TABLE_DISTRITO_INSERT)
            cargarDatosDesdeCSV(db, "direccion_usuario.csv", TABLE_DIRECCION_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "cuenta_admin.csv", TABLE_CUENTA_ADMIN_INSERT)
            cargarDatosDesdeCSV(db, "operacion.csv", TABLE_OPERACION_INSERT)
            cargarDatosDesdeCSV(db, "tarjeta_usuario.csv", TABLE_TARJETA_USUARIO_INSERT)
            cargarDatosDesdeCSV(db, "registro_recarga.csv", TABLE_REGISTRO_RECARGA_INSERT)
            cargarDatosDesdeCSV(db, "acceso_empleado.csv", TABLE_ACCESO_EMPLEADO_INSERT)*/

            db.setTransactionSuccessful()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
        return dbHelper
    }

    private fun cargarDatosDesdeCSV(db: SQLiteDatabase, fileName: String, insertStatement: String) {
        val inputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val values = line!!.split(";")
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
        const val TABLE_ADMINISTRADOR_INSERT = "INSERT INTO administrador (id_admin, email) VALUES (?, ?)"
        const val TABLE_CLIENTE_INSERT = "INSERT INTO cliente (dni_cliente, historial_cretidicio) VALUES (?, ?)"
        const val TABLE_USUARIO_INSERT = "INSERT INTO usuario (num_movil, dni_usuario, fecha_inicio, observaciones) VALUES (?, ?, ?, ?)"
        const val TABLE_CUENTA_USUARIO_INSERT = "INSERT INTO cuenta_usuario (num_movil_usuario, saldo, ip_cuenta_usuario, contraseña, limite_por_dia, email, estado_de_actividad) VALUES (?, ?, ?, ?, ?, ?, ?)"
        const val TABLE_DEPARTAMENTO_INSERT = "INSERT INTO departamento (id_departamento, nombre_departamento, descripcion) VALUES (?, ?, ?)"
        const val TABLE_PROVINCIA_INSERT = "INSERT INTO provincia (id_departamento, id_provincia, nombre_provincia, descripcion) VALUES (?, ?, ?, ?)"
        const val TABLE_DISTRITO_INSERT = "INSERT INTO distrito (id_distrito, id_provincia, nombre_distrito, descripcion) VALUES (?, ?, ?, ?)"
        const val TABLE_DIRECCION_USUARIO_INSERT = "INSERT INTO direccion_usuario (num_movil_usuario, direcccion_exacta, id_departamento, id_provincia, id_distrito) VALUES (?, ?, ?, ?, ?)"
        const val TABLE_CUENTA_ADMIN_INSERT = "INSERT INTO cuenta_admin (id_cuenta_admin, ip_cuenta_admin, contraseña, estado_de_actividad) VALUES (?, ?, ?, ?)"
        const val TABLE_OPERACION_INSERT = "INSERT INTO operacion (cuenta_origen, cuenta_destino, hora_operacion, fecha_operacion, monto_operacion) VALUES (?, ?, ?, ?, ?)"
        const val TABLE_TARJETA_USUARIO_INSERT = "INSERT INTO tarjeta_usuario (num_tarjeta, num_movil_usuario, fecha_vencimiento, codigo_csv) VALUES (?, ?, ?, ?)"
        const val TABLE_REGISTRO_RECARGA_INSERT = "INSERT INTO registro_recarga (num_tarjeta, codigo_generado, monto, fecha, hora, estado_recarga) VALUES (?, ?, ?, ?, ?, ?)"
        const val TABLE_ACCESO_EMPLEADO_INSERT = "INSERT INTO acceso_empleado (num_movil_empleador, num_movil_empleado, estado_acceso, fecha_acceso) VALUES (?, ?, ?, ?)"
    }
}
