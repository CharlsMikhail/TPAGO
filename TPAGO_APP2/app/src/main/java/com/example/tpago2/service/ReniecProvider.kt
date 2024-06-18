package com.example.tpago2.service

import com.example.tpago2.data.entidades.Persona

class ReniecProvider {
    companion object {
        val listUserReniec = arrayListOf<Persona>(
            Persona(12345678, "Juan", "Carlos", "González", "Pérez"),
            Persona(23456789, "María", "Luisa", "Rodríguez", "Fernández"),
            Persona(34567890, "Pedro", "Antonio", "Martínez", "Gómez"),
            Persona(45678901, "Ana", "Isabel", "López", "Díaz"),
            Persona(56789012, "Luis", "Miguel", "Hernández", "Sánchez"),
            Persona(67890123, "Laura", "Beatriz", "Jiménez", "Torres"),
            Persona(78901234, "Carlos", "Andrés", "Álvarez", "Ramírez"),
            Persona(89012345, "Sofía", "Verónica", "Morales", "Castro"),
            Persona(90123456, "Miguel", "Ángel", "Ortiz", "Rubio"),
            Persona(12345679, "Lucía", "Alejandra", "Navarro", "Romero"),
            Persona(72866150, "Carlos", "Leandro", "Vilca", "Estrada"),
            Persona(72866151, "Maria", "Juana", "Pilco", "Zambrano"),
            Persona(11112222, "Jorge", "Luis", "García", "Paredes"),
            Persona(22223333, "Elena", "María", "Sánchez", "Rojas"),
            Persona(33334444, "Roberto", "Carlos", "Mendoza", "Reyes"),
            Persona(44445555, "Patricia", "Beatriz", "Guzmán", "Soto"),
            Persona(55556666, "Fernando", "José", "Paredes", "Gómez"),
            Persona(66667777, "Mónica", "Rosario", "Chávez", "Flores"),
            Persona(77778888, "Gustavo", "Adolfo", "Vega", "Suárez"),
            Persona(88889999, "Verónica", "Elizabeth", "Ramírez", "Ortega"),
            Persona(99990000, "Alberto", "Miguel", "Campos", "Silva"),
            Persona(10101010, "Gloria", "Patricia", "Luna", "Jiménez"),
            Persona(20202020, "César", "Antonio", "Pizarro", "Vargas"),
            Persona(30303030, "Daniela", "María", "Peña", "Herrera"),
            Persona(40404040, "Hugo", "Ricardo", "Montes", "Zavala"),
            Persona(50505050, "Gabriela", "Elena", "Ríos", "Pérez"),
            Persona(60606060, "Mauricio", "Raúl", "Tapia", "Campos"),
            Persona(70707070, "Valeria", "Mariana", "Franco", "López"),
            Persona(80808080, "Esteban", "Ignacio", "Torres", "Ramos"),
            Persona(90909090, "Inés", "Alejandra", "Quispe", "García"),
            Persona(10111212, "Renato", "Andrés", "Moreno", "Navarro"),
            Persona(21222323, "Claudia", "Juliana", "Fuentes", "Medina"),
            Persona(32333434, "Ricardo", "José", "Guzmán", "Rosales"),
            Persona(43444545, "María", "Cristina", "Santos", "Rojas"),
            Persona(54555656, "Ernesto", "Miguel", "Espinoza", "Salazar"),
            Persona(65666767, "Diana", "Patricia", "Fernández", "Paredes"),
            Persona(76777878, "Andrés", "Felipe", "Medina", "Lozano"),
            Persona(87888989, "Natalia", "Andrea", "Ortega", "Mora"),
            Persona(98990001, "Julio", "César", "Reyes", "Paredes"),
            Persona(10203040, "Marta", "Rocío", "García", "Chávez"),
            Persona(20304050, "Diego", "Fernando", "Hurtado", "Vásquez"),
            Persona(30405060, "Victoria", "Daniela", "Núñez", "Carrasco"),
            Persona(40506070, "Juan", "Sebastián", "Pérez", "Contreras"),
            Persona(50607080, "Elisa", "María", "Aguilar", "Hernández"),
            Persona(60708090, "Tomás", "Miguel", "Valencia", "Figueroa"),
            Persona(70809001, "Olga", "Patricia", "Cornejo", "Rivas"),
            Persona(80901011, "Matías", "Leonardo", "Chavez", "Espinoza"),
            Persona(91011121, "Rosa", "Marina", "Alarcón", "Salinas"),
            Persona(11213141, "Javier", "Carlos", "Montero", "Escobar"),
            Persona(987654320, "David", "Enrique", "Castillo", "Serrano"),
            Persona(976543219, "Elena", "Patricia", "Mendoza", "Garcia"),
            Persona(965432198, "Alberto", "Rafael", "Soto", "Jimenez"),
            Persona(954321087, "Julia", "Consuelo", "Dominguez", "Moreno"),
            Persona(943210976, "Fernando", "Ignacio", "Gil", "Ortiz"),
            Persona(932109865, "Ana", "Carolina", "Pérez", "Reyes"),
            Persona(921098754, "Jose", "Manuel", "Ruiz", "Alvarez"),
            Persona(910987643, "Sandra", "Cecilia", "Ramirez", "Guzman"),
            Persona(909876532, "Ricardo", "Javier", "Herrera", "Ponce"),
            Persona(998765421, "Patricia", "Teresa", "Vargas", "Medina")
        )
        fun getPersonaByDni(dni: Int): Persona? {
            return ReniecProvider.listUserReniec.find { it.dni_persona == dni }
        }
    }
}
