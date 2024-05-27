package com.example.tpago2.bd


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PERSONA)
        db?.execSQL(CREATE_TABLE_CUENTA_ADMINISTRADOR)
        db?.execSQL(CREATE_TABLE_USUARIO)
        db?.execSQL(CREATE_TABLE_CUENTA_USUARIO)
        db?.execSQL(CREATE_TABLE_DEPARTAMENTO)
        db?.execSQL(CREATE_TABLE_PROVINCIA)
        db?.execSQL(CREATE_TABLE_DISTRITO)
        db?.execSQL(CREATE_TABLE_DIRECCION_USUARIO)
        db?.execSQL(CREATE_TABLE_OPERACION)
        db?.execSQL(CREATE_TABLE_TARJETA_USUARIO)
        db?.execSQL(CREATE_TABLE_RECARGA)
        db?.execSQL(CREATE_TABLE_ACCESO_EMPLEADO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS persona")
        db?.execSQL("DROP TABLE IF EXISTS cuenta_administrador")
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        db?.execSQL("DROP TABLE IF EXISTS cuenta_usuario")
        db?.execSQL("DROP TABLE IF EXISTS departamento")
        db?.execSQL("DROP TABLE IF EXISTS provincia")
        db?.execSQL("DROP TABLE IF EXISTS distrito")
        db?.execSQL("DROP TABLE IF EXISTS direccion_usuario")
        db?.execSQL("DROP TABLE IF EXISTS operacion")
        db?.execSQL("DROP TABLE IF EXISTS tarjeta_usuario")
        db?.execSQL("DROP TABLE IF EXISTS recarga")
        db?.execSQL("DROP TABLE IF EXISTS acceso_empleado")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "tpago.db"
        private const val DATABASE_VERSION = 1

        const val CREATE_TABLE_PERSONA = """
            CREATE TABLE persona (
                dni_persona INTEGER NOT NULL PRIMARY KEY,
                primer_nombre TEXT NOT NULL,
                segundo_nombre TEXT,
                ape_paterno TEXT NOT NULL,
                ape_materno TEXT NOT NULL
            )
        """

        const val CREATE_TABLE_CUENTA_ADMINISTRADOR = """
            CREATE TABLE cuenta_administrador (
                dni_persona INTEGER NOT NULL PRIMARY KEY REFERENCES persona(dni_persona),
                email TEXT,
                ip_cuenta_admin TEXT NOT NULL,
                contrasenia TEXT NOT NULL,
                estado_de_actividad INTEGER NOT NULL
            )
        """

        const val CREATE_TABLE_USUARIO = """
            CREATE TABLE usuario (
                dni_persona INTEGER NOT NULL PRIMARY KEY REFERENCES persona(dni_persona),
                fecha_inicio DATE NOT NULL,
                observaciones TEXT
            )
        """

        const val CREATE_TABLE_CUENTA_USUARIO = """
            CREATE TABLE cuenta_usuario (
                num_movil INTEGER NOT NULL PRIMARY KEY,
                dni_persona INTEGER NOT NULL REFERENCES usuario(dni_persona),
                saldo INTEGER NOT NULL,
                ip_cuenta_usuario TEXT NOT NULL,
                contrasenia INTEGER NOT NULL,
                limite_por_transaccion INTEGER NOT NULL,
                email TEXT,
                estado_de_actividad INTEGER
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
                id_departamento INTEGER NOT NULL REFERENCES departamento(id_departamento),
                id_provincia INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                nombre_provincia TEXT NOT NULL,
                descripcion TEXT
            )
        """

        const val CREATE_TABLE_DISTRITO = """
            CREATE TABLE distrito (
                id_provincia INTEGER NOT NULL REFERENCES provincia(id_provincia),
                id_distrito INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                nombre_distrito TEXT NOT NULL,
                descripcion TEXT
            )
        """

        const val CREATE_TABLE_DIRECCION_USUARIO = """
            CREATE TABLE direccion_usuario (
                num_movil INTEGER NOT NULL PRIMARY KEY REFERENCES cuenta_usuario(num_movil),
                direccion_exacta TEXT NOT NULL,
                id_distrito INTEGER NOT NULL REFERENCES distrito(id_distrito)
            )
        """

        const val CREATE_TABLE_OPERACION = """
            CREATE TABLE operacion (
                id_operacion INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                num_cuenta_origen INTEGER NOT NULL REFERENCES cuenta_usuario(num_movil),
                num_cuenta_destino INTEGER NOT NULL REFERENCES cuenta_usuario(num_movil),
                hora_operacion TIME NOT NULL,
                fecha_operacion DATE NOT NULL,
                monto_operacion INTEGER NOT NULL
            )
        """

        const val CREATE_TABLE_TARJETA_USUARIO = """
            CREATE TABLE tarjeta_usuario (
                num_tarjeta TEXT NOT NULL PRIMARY KEY,
                num_movil INTEGER NOT NULL REFERENCES cuenta_usuario(num_movil),
                fecha_vencimiento DATE NOT NULL,
                codigo_csv TEXT NOT NULL
            )
        """

        const val CREATE_TABLE_RECARGA = """
            CREATE TABLE recarga (
                id_recarga INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                tipo TEXT NOT NULL,
                num_tarjeta TEXT REFERENCES tarjeta_usuario(num_tarjeta),
                codigo_generado TEXT,
                monto INTEGER NOT NULL,
                fecha DATE NOT NULL,
                hora TIME NOT NULL
            )
        """

        const val CREATE_TABLE_ACCESO_EMPLEADO = """
            CREATE TABLE acceso_empleado (
                id_acceso INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                num_movil_empleador INTEGER NOT NULL REFERENCES cuenta_usuario(num_movil),
                num_movil_empleado INTEGER NOT NULL REFERENCES cuenta_usuario(num_movil),
                estado_acceso INTEGER NOT NULL,
                fecha_acceso DATE NOT NULL,
                UNIQUE(num_movil_empleador, num_movil_empleado)
            )
        """

        @Volatile
        private var instance: DataBaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DataBaseHelper {
            return instance ?: synchronized(this) {
                instance ?: DataBaseHelper(context.applicationContext).also { instance = it }
            }
        }
    }
}
