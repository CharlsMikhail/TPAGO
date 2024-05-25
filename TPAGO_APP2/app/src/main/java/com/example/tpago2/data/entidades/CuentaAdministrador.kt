package com.example.tpago2.data.entidades

import java.io.Serializable

data class CuentaAdministrador(
    val dni_persona: Int,
    val email: String?,
    val ip_cuenta_admin: String,
    val contrasenia: String,
    val estado_de_actividad: Int
): Serializable