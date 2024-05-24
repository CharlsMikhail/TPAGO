package com.example.tpago2.data.entidades

import java.io.Serializable
import java.sql.Date

data class Usuario(
    var nombre: String,
    var edad: Int,
    var email: String,
    var password: String
)
