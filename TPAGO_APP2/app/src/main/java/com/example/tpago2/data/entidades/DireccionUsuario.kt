package com.example.tpago2.data.entidades

import java.io.Serializable

data class DireccionUsuario(
    val num_movil: String,
    val direccion_exacta: String,
    val id_distrito: Int
): Serializable
