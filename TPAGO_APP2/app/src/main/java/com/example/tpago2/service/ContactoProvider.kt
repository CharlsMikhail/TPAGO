package com.example.tpago2.service

import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.Usuario2

class ContactoProvider {
    companion object {
        val listaContactos = arrayListOf<Contacto>(
            Contacto("Maria Bustamante", 986574214),
            Contacto("Alicia Juarez", 998765432),
            Contacto("Pedro Mamani", 965432109),
            Contacto("Juan Del Castillo", 987654321),
            Contacto("Juan Quispe", 946813579),
            Contacto("Patricio Gonzales", 197900995),
            Contacto("Yony Vilca", 997979070),
            Contacto("Erika Gonzales", 986986869),
            Contacto("Wos Perez", 974905279),
            Contacto("Laura Aragon", 932109876),
        )
    }
}