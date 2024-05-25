package com.example.tpago2.data.entidades

import java.io.Serializable

data class Operacion(
    val id_operacion: Int,
    val num_cuenta_origen: Int,
    val num_cuenta_destino: Int,
    val hora_operacion: String,
    val fecha_operacion: String,
    val monto_operacion: Int
): Serializable
