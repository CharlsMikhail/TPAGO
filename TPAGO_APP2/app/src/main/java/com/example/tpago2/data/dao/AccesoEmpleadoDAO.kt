package com.example.tpago2.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tpago2.bd.DataBaseHelper
import com.example.tpago2.data.entidades.AccesoAndChamba

class AccesoEmpleadoDAO(context: Context) {

    private val dbHelper: DataBaseHelper = DataBaseHelper.getInstance(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    // Insertar un nuevo acceso de empleado
    fun insertarAccesoEmpleado(
        numMovilEmpleador: Int,
        numMovilEmpleado: Int,
        estadoAcceso: Int,
        fechaAcceso: String
    ): Long {
        val contentValues = ContentValues().apply {
            put("num_movil_empleador", numMovilEmpleador)
            put("num_movil_empleado", numMovilEmpleado)
            put("estado_acceso", estadoAcceso)
            put("fecha_acceso", fechaAcceso)
        }
        return db.insert("acceso_empleado", null, contentValues)
    }

    // Obtener todos los accesos de un empleado por su número de móvil
    @SuppressLint("Range")
    fun obtenerAccesosPorEmpleado(numMovilEmpleado: Int): MutableList<AccesoAndChamba> {
        val accesos: MutableList<AccesoAndChamba> = ArrayList()
        val query = """
            SELECT ae.id_acceso as idAccesoOrChamba, cu.num_movil, p.primer_nombre || ' ' || p.ape_paterno || ' ' || p.ape_materno as nombres
            FROM acceso_empleado ae
            INNER JOIN cuenta_usuario cu ON ae.num_movil_empleador = cu.num_movil
            INNER JOIN persona p ON cu.dni_persona = p.dni_persona
            WHERE ae.num_movil_empleado = ?
            ORDER BY ae.fecha_acceso DESC
        """
        val cursor: Cursor = db.rawQuery(query, arrayOf(numMovilEmpleado.toString()))
        if (cursor.moveToFirst()) {
            do {
                val acceso = AccesoAndChamba(
                    cursor.getInt(cursor.getColumnIndex("idAccesoOrChamba")),
                    cursor.getInt(cursor.getColumnIndex("num_movil")),
                    cursor.getString(cursor.getColumnIndex("nombres"))
                )
                accesos.add(acceso)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return accesos
    }

    // Obtener todos los accesos por empleador por su número de móvil
    @SuppressLint("Range")
    fun obtenerAccesosPorEmpleador(numMovilEmpleador: Int): MutableList<AccesoAndChamba> {
        val accesos: MutableList<AccesoAndChamba> = ArrayList()
        val query = """
            SELECT ae.id_acceso as idAccesoOrChamba, cu.num_movil, p.primer_nombre || ' ' || p.ape_paterno || ' ' || p.ape_materno as nombres
            FROM acceso_empleado ae
            INNER JOIN cuenta_usuario cu ON ae.num_movil_empleado = cu.num_movil
            INNER JOIN persona p ON cu.dni_persona = p.dni_persona
            WHERE ae.num_movil_empleador = ?
            ORDER BY ae.fecha_acceso DESC
        """
        val cursor: Cursor = db.rawQuery(query, arrayOf(numMovilEmpleador.toString()))
        if (cursor.moveToFirst()) {
            do {
                val acceso = AccesoAndChamba(
                    cursor.getInt(cursor.getColumnIndex("idAccesoOrChamba")),
                    cursor.getInt(cursor.getColumnIndex("num_movil")),
                    cursor.getString(cursor.getColumnIndex("nombres"))
                )
                accesos.add(acceso)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return accesos
    }


    // Actualizar un acceso de empleado
    fun actualizarAccesoEmpleado(
        idAcceso: Int,
        numMovilEmpleador: Int,
        numMovilEmpleado: Int,
        estadoAcceso: Int,
        fechaAcceso: String
    ): Int {
        val contentValues = ContentValues().apply {
            put("num_movil_empleador", numMovilEmpleador)
            put("num_movil_empleado", numMovilEmpleado)
            put("estado_acceso", estadoAcceso)
            put("fecha_acceso", fechaAcceso)
        }
        return db.update(
            "acceso_empleado",
            contentValues,
            "id_acceso = ?",
            arrayOf(idAcceso.toString())
        )
    }

    // Eliminar un acceso de empleado por su id
    fun eliminarAccesoEmpleado(idAcceso: Int): Int {
        return db.delete("acceso_empleado", "id_acceso = ?", arrayOf(idAcceso.toString()))
    }

    // Verificar si un acceso de empleado existe
    fun verificarAccesoExistente(numMovilEmpleador: Int, numMovilEmpleado: Int): Boolean {
        val cursor: Cursor = db.query(
            "acceso_empleado",
            null,
            "num_movil_empleador = ? AND num_movil_empleado = ?",
            arrayOf(numMovilEmpleador.toString(), numMovilEmpleado.toString()),
            null,
            null,
            null
        )
        val existe = cursor.moveToFirst()
        cursor.close()
        return existe
    }

    @SuppressLint("Range")
    fun obtenerCantidadEmpleadosVinculados(numMovilEmpleador: Int): Int {
        val query = """
        SELECT COUNT(*) as total
        FROM acceso_empleado
        WHERE num_movil_empleador = ?
    """
        val cursor: Cursor = db.rawQuery(query, arrayOf(numMovilEmpleador.toString()))
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("total"))
        }
        cursor.close()
        return total
    }

    @SuppressLint("Range")
    fun verificarEmpleadoDelEmpleador(numMovilEmpleador: Int, numMovilEmpleado: Int): Boolean {
        val query = """
        SELECT COUNT(*) as total
        FROM acceso_empleado
        WHERE num_movil_empleador = ? AND num_movil_empleado = ?
    """
        val cursor: Cursor = db.rawQuery(query, arrayOf(numMovilEmpleador.toString(), numMovilEmpleado.toString()))
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("total"))
        }
        cursor.close()
        return total > 0
    }



}
