-- Creación de base de datos para el sitema T'PAGO, un monedore virtual independiente.
-- Materia: Procesos de Software - ULS.
-- Colaboradores: Carlos Mamani, Yony Vilca, Leandro Estrada y Esthephany Choquehuanca.
-- Fecha de creación: 15/04/2024
-- Fecha de modificación: 01/05/2024

create table persona (
	dni_persona serial not null primary key,
	primer_nombre varchar(20) not null,
	segundo_nombre varchar(20) null,
	ape_paterno varchar(20) not null,
	ape_materno varchar(20) not null
);

create table cliente (
	id_cliente serial not null primary key,
	historial_cretidicio text not null, -- un link
	dni_persona integer not null references persona(dni_persona)
);

create table administrador (
	id_admin serial not null primary key,
	dni_persona integer not null references persona(dni_persona),
	email varchar(50) null
);

-- Un cliente puede tener muchas cuentas de usuario.
create table usuario ( 
	id_usuario serial not null primary key,
	id_cliente integer not null references cliente(id_cliente),
	fecha_inicio date not null, 
	observaciones text null
);

create table cuenta_usuario (
	id_cuenta_usuario serial not null primary key,
	id_usuario integer not null references usuario(id_usuario),
	num_movil varchar(20) not null,
	saldo decimal(10, 5) not null,
	ip_cuenta_usuario varchar(32) not null,
	contraseña varchar(10) not null,
	limite_por_dia decimal(10,3) not null,
	email varchar(50) null,
	estado_de_actividad bool, -- Por si esta suspendido.
	unique(num_movil)
);

create table direccion_usuario_cuenta (
	id_cuenta_usuario integer not null primary key references cuenta_usuario(id_cuenta_usuario),
	direcccion_exacta varchar(100) not null,
	distrito varchar(30) not null,
    provincia varchar(30) not null,
    departamento varchar(30) not null,
    codigo_postal varchar(10) null
);

-- Por el momento tiene todos los permisos.
-- Solo existe una cuenta administrador.
create table cuenta_admin (
	id_cuenta_admin serial not null primary key,
	id_admin integer not null references administrador(id_admin),
	ip_cuenta_admin varchar(32) not null,
	contraseña varchar(10) not null,
	estado_de_actividad bool not null
);

-- Aqui esta el regristo de la operación
create table operacion ( 
	id_operacion serial not null primary key,
	cuenta_origen integer not null references cuenta_usuario(id_cuenta_usuario),
	cuenta_destino integer not null references cuenta_usuario(id_cuenta_usuario),
	hora_operacion time not null,
	fecha_operacion date not null,
	monto_operacion decimal(10, 3) not null
);

create table tarjeta_usuario (
	num_tarjeta varchar(20) not null primary key,
	id_usuario integer not null references usuario(id_usuario),
	fecha_vencimiento date not null,
	codigo_csv varchar(3) not null
);

create table registro_recarga (
	id_registro_recarga serial not null primary key,
	-- Uno de los dos tiene que rellenarse, no pueden ser nulos los dos
	num_tarjeta varchar(20)  null references tarjeta_usuario(num_tarjeta), -- Primer método de recarga
	codigo_generado varchar(30) null, -- Segundo método de recarga
	monto decimal(3,3) not null,
	fecha date not null,
	hora time not null,
	estado_recarga varchar(20) not null -- Si esta en proceso o se consolido la recarga.   
);

create table planilla_empleado (
	id_acceso serial not null primary key,
	id_usuario_empleador integer not null references usuario(id_usuario),
	id_usuario_empleado integer not null references usuario(id_usuario),
	estado_acceso bool not null, -- si aun tiene el acceso.
	fecha_acceso date not null,
	unique(id_usuario_empleador, id_usuario_empleado)
);

-- ¿Se guardaran un registro de las recargas?



