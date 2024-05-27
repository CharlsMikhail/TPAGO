package com.example.tpago2.data.entidades

import java.io.Serializable

data class AccesoEmpleado(
    val id_acceso: Int,
    val num_movil_empleador: String,
    val num_movil_empleado: String,
    val estado_acceso: Int,
    val fecha_acceso: String
): Serializable
