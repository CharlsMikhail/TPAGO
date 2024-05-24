package com.example.tpago2.data.entidades

import java.io.Serializable

data class CuentaUsuario(
    var usuario: User,
    var saldo: Int,
    var ipCuenta: String,
    var contrasenia: String,
    var limitePorDia: Int,
    var email: String,
    var estado: Boolean
): Serializable

