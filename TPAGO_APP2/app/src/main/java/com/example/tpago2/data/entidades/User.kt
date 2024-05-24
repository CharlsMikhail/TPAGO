package com.example.tpago2.data.entidades

import java.io.Serializable

data class User(
    var cliente: Cliente,
    var numMovil: String,
    var fechaInicio: String,
    var observaciones: String,
): Serializable
