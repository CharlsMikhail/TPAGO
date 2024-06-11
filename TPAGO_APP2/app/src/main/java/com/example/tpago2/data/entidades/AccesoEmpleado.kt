package com.example.tpago2.data.entidades

import java.io.Serializable

data class AccesoEmpleado(
    val id_acceso: Int,
    val num_movil_empleador: Int,
    val num_movil_empleado: Int,
    val estado_acceso: Int,
    val fecha_acceso: String
): Serializable
