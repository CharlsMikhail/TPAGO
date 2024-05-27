package com.example.tpago2.data.entidades

import java.io.Serializable

data class Distrito(
    val id_distrito: Int,
    val id_provincia: Int,
    val nombre_distrito: String,
    val descripcion: String?
): Serializable