package com.example.tpago2.data.entidades

import java.io.Serializable

data class Cliente(
    var persona: Persona,
    var historialCliente: String
): Serializable