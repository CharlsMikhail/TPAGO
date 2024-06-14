package com.example.tpago2.data.entidades

import java.io.Serializable

data class Movimiento(
    val nombre: String,
    val apePaterno: String,
    val apeMaterno: String,
    val movil: Int,
    val monto: Int,
    val fecha: String,
    val hora: String,
    val tipo: String
): Serializable
