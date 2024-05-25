package com.example.tpago2.data.entidades

import java.io.Serializable

data class Recarga(
    val id_recarga: Int,
    val tipo: String,
    val num_tarjeta: String?,
    val codigo_generado: String?,
    val monto: Int,
    val fecha: String,
    val hora: String
): Serializable
