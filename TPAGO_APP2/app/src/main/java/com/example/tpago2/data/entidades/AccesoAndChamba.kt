package com.example.tpago2.data.entidades

import java.io.Serializable
data class AccesoAndChamba(
    val idAccesoOrChamba: Int,
    val numMovil: Int,
    var nombres: String
): Serializable
