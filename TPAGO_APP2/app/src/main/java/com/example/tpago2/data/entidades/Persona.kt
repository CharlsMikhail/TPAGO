package com.example.tpago2.data.entidades

import java.io.Serializable

data class Persona(
    val dni_persona: Int,
    val primer_nombre: String,
    val segundo_nombre: String?,
    val ape_paterno: String,
    val ape_materno: String
): Serializable
