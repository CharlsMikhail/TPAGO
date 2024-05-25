package com.example.tpago2.data.entidades

import java.io.Serializable

data class CuentaUsuario(
    val num_movil: String,
    val dni_persona: Int,
    val saldo: Int,
    val ip_cuenta_usuario: String,
    val contrasenia: String,
    val limite_por_transaccion: Int,
    val email: String?,
    val estado_de_actividad: Int
): Serializable

