package com.example.tpago2.data.entidades

import java.io.Serializable

data class TarjetaUsuario(
    val num_tarjeta: String,
    val num_movil: Int,
    val fecha_vencimiento: String,
    val codigo_csv: String
): Serializable