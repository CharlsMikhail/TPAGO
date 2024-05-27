package com.example.tpago2.data.entidades

import java.io.Serializable

data class Departamento(
    val id_departamento: Int,
    val nombre_departamento: String,
    val descripcion: String?
): Serializable
