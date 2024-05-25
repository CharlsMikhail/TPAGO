package com.example.tpago2.data.entidades

import java.io.Serializable

data class Provincia(
    val id_departamento: Int,
    val id_provincia: Int,
    val nombre_provincia: String,
    val descripcion: String?
): Serializable