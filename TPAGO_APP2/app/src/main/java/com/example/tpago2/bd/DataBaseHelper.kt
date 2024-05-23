package com.example.tpago2.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "tpago.db"
const val DATABASE_VERSION = 1

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //Un singleton
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PERSONA)
        db?.execSQL(CREATE_TABLE_ADMINISTRADOR)
        db?.execSQL(CREATE_TABLE_CLIENTE)
        db?.execSQL(CREATE_TABLE_USUARIO)
        db?.execSQL(CREATE_TABLE_CUENTA_USUARIO)
        db?.execSQL(CREATE_TABLE_DEPARTAMENTO)
        db?.execSQL(CREATE_TABLE_PROVINCIA)
        db?.execSQL(CREATE_TABLE_DISTRITO)
        db?.execSQL(CREATE_TABLE_DIRECCION_USUARIO)
        db?.execSQL(CREATE_TABLE_CUENTA_ADMIN)
        db?.execSQL(CREATE_TABLE_OPERACION)
        db?.execSQL(CREATE_TABLE_TARJETA_USUARIO)
        db?.execSQL(CREATE_TABLE_REGISTRO_RECARGA)
        db?.execSQL(CREATE_TABLE_ACCESO_EMPLEADO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS persona")
        db?.execSQL("DROP TABLE IF EXISTS administrador")
        db?.execSQL("DROP TABLE IF EXISTS cliente")
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        db?.execSQL("DROP TABLE IF EXISTS cuenta_usuario")
        db?.execSQL("DROP TABLE IF EXISTS departamento")
        db?.execSQL("DROP TABLE IF EXISTS provincia")
        db?.execSQL("DROP TABLE IF EXISTS distrito")
        db?.execSQL("DROP TABLE IF EXISTS direccion_usuario")
        db?.execSQL("DROP TABLE IF EXISTS cuenta_admin")
        db?.execSQL("DROP TABLE IF EXISTS operacion")
        db?.execSQL("DROP TABLE IF EXISTS tarjeta_usuario")
        db?.execSQL("DROP TABLE IF EXISTS registro_recarga")
        db?.execSQL("DROP TABLE IF EXISTS acceso_empleado")
        onCreate(db)
    }

    companion object {
        const val CREATE_TABLE_PERSONA = """
            CREATE TABLE persona (
                dni_persona TEXT NOT NULL PRIMARY KEY,
                primer_nombre TEXT NOT NULL,
                segundo_nombre TEXT,
                ape_paterno TEXT NOT NULL,
                ape_materno TEXT NOT NULL
            )
        """

        const val CREATE_TABLE_ADMINISTRADOR = """
            CREATE TABLE administrador (
                id_admin TEXT NOT NULL PRIMARY KEY,
                email TEXT,
                FOREIGN KEY (id_admin) REFERENCES persona(dni_persona)
            )
        """

        const val CREATE_TABLE_CLIENTE = """
            CREATE TABLE cliente (
                dni_cliente TEXT NOT NULL PRIMARY KEY,
                historial_cretidicio TEXT NOT NULL,
                FOREIGN KEY (dni_cliente) REFERENCES persona(dni_persona)
            )
        """

        const val CREATE_TABLE_USUARIO = """
            CREATE TABLE usuario (
                num_movil TEXT NOT NULL PRIMARY KEY,
                dni_usuario TEXT NOT NULL,
                fecha_inicio DATE NOT NULL,
                observaciones TEXT,
                FOREIGN KEY (dni_usuario) REFERENCES cliente(dni_cliente)
            )
        """

        const val CREATE_TABLE_CUENTA_USUARIO = """
            CREATE TABLE cuenta_usuario (
                num_movil_usuario TEXT NOT NULL PRIMARY KEY,
                saldo INTEGER NOT NULL,
                ip_cuenta_usuario TEXT NOT NULL,
                contraseña TEXT NOT NULL,
                limite_por_dia INTEGER NOT NULL,
                email TEXT,
                estado_de_actividad BOOLEAN NOT NULL,
                FOREIGN KEY (num_movil_usuario) REFERENCES usuario(num_movil)
            )
        """

        const val CREATE_TABLE_DEPARTAMENTO = """
            CREATE TABLE departamento (
                id_departamento INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                nombre_departamento TEXT NOT NULL,
                descripcion TEXT
            )
        """

        const val CREATE_TABLE_PROVINCIA = """
            CREATE TABLE provincia (
                id_departamento INTEGER NOT NULL,
                id_provincia INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                nombre_provincia TEXT NOT NULL,
                descripcion TEXT,
                FOREIGN KEY (id_departamento) REFERENCES departamento(id_departamento)
            )
        """

        const val CREATE_TABLE_DISTRITO = """
            CREATE TABLE distrito (
                id_distrito INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                id_provincia INTEGER NOT NULL,
                nombre_distrito TEXT NOT NULL,
                descripcion TEXT,
                FOREIGN KEY (id_provincia) REFERENCES provincia(id_provincia)
            )
        """

        const val CREATE_TABLE_DIRECCION_USUARIO = """
            CREATE TABLE direccion_usuario (
                num_movil_usuario TEXT NOT NULL PRIMARY KEY,
                direcccion_exacta TEXT NOT NULL,
                id_departamento INTEGER NOT NULL,
                id_provincia INTEGER NOT NULL,
                id_distrito INTEGER NOT NULL,
                FOREIGN KEY (num_movil_usuario) REFERENCES usuario(num_movil),
                FOREIGN KEY (id_departamento) REFERENCES departamento(id_departamento),
                FOREIGN KEY (id_provincia) REFERENCES provincia(id_provincia),
                FOREIGN KEY (id_distrito) REFERENCES distrito(id_distrito)
            )
        """

        const val CREATE_TABLE_CUENTA_ADMIN = """
            CREATE TABLE cuenta_admin (
                id_cuenta_admin TEXT NOT NULL PRIMARY KEY,
                ip_cuenta_admin TEXT NOT NULL,
                contraseña TEXT NOT NULL,
                estado_de_actividad BOOLEAN NOT NULL,
                FOREIGN KEY (id_cuenta_admin) REFERENCES administrador(id_admin)
            )
        """

        const val CREATE_TABLE_OPERACION = """
            CREATE TABLE operacion (
                id_operacion INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                cuenta_origen TEXT NOT NULL,
                cuenta_destino TEXT NOT NULL,
                hora_operacion TIME NOT NULL,
                fecha_operacion DATE NOT NULL,
                monto_operacion INTEGER NOT NULL,
                FOREIGN KEY (cuenta_origen) REFERENCES cuenta_usuario(num_movil_usuario),
                FOREIGN KEY (cuenta_destino) REFERENCES cuenta_usuario(num_movil_usuario)
            )
        """

        const val CREATE_TABLE_TARJETA_USUARIO = """
            CREATE TABLE tarjeta_usuario (
                num_tarjeta TEXT NOT NULL PRIMARY KEY,
                num_movil_usuario TEXT NOT NULL,
                fecha_vencimiento DATE NOT NULL,
                codigo_csv TEXT NOT NULL,
                FOREIGN KEY (num_movil_usuario) REFERENCES usuario(num_movil)
            )
        """

        const val CREATE_TABLE_REGISTRO_RECARGA = """
            CREATE TABLE registro_recarga (
                id_registro_recarga INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                num_tarjeta TEXT,
                codigo_generado TEXT,
                monto INTEGER NOT NULL,
                fecha DATE NOT NULL,
                hora TIME NOT NULL,
                estado_recarga BOOLEAN NOT NULL,
                FOREIGN KEY (num_tarjeta) REFERENCES tarjeta_usuario(num_tarjeta)
            )
        """

        const val CREATE_TABLE_ACCESO_EMPLEADO = """
            CREATE TABLE acceso_empleado (
                id_acceso INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                num_movil_empleador TEXT NOT NULL,
                num_movil_empleado TEXT NOT NULL,
                estado_acceso BOOLEAN NOT NULL,
                fecha_acceso DATE NOT NULL,
                FOREIGN KEY (num_movil_empleador) REFERENCES cuenta_usuario(num_movil_usuario),
                FOREIGN KEY (num_movil_empleado) REFERENCES cuenta_usuario(num_movil_usuario),
                UNIQUE(num_movil_empleador, num_movil_empleado)
            )
        """
    }
}
