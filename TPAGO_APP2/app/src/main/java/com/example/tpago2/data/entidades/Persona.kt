package com.example.tpago2.data.entidades

import java.io.Serializable

data class Persona(
    var dniPersona: String,
    var primerNombre: String,
    var segundoNombre:String,
    var apePaterno: String,
    var apeMaterno: String
): Serializable
