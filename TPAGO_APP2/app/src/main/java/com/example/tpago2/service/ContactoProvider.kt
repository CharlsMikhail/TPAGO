package com.example.tpago2.service

import com.example.tpago2.data.entidades.Contacto
import com.example.tpago2.data.entidades.Usuario2

class ContactoProvider {
    companion object {
        val listaContactos = arrayListOf<Contacto>(
            Contacto("carlos bustamante", 929016170),
            Contacto("alicia juarez", 123456789),
            Contacto("pedro mamani", 987654321),
            Contacto("pepe del castillo", 135792468),
            Contacto("juan quispe", 246813579),
            Contacto("patricio parodi", 197900995),
            Contacto("yony vilca", 797979070),
            Contacto("erika gonzales", 986986869),
            Contacto("wos perez", 574905279),
            Contacto("abel aragon", 589328494),
        )
    }
}