package com.example.tpago2.data.entidades

import java.io.Serializable

data class Usuario(
    val dni_persona: Int,
    val fecha_inicio: String,
    val observaciones: String?
): Serializable
