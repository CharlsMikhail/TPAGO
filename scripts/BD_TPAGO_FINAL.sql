-- Creación de base de datos para el sitema T'PAGO, un monedore virtual independiente.
-- Materia: Procesos de Software - ULS.
-- Colaboradores: Carlos Mamani, Yony Vilca, Leandro Estrada y Esthephany Choquehuanca.
-- Fecha de creación: 15/04/2024
-- Fecha de modificación: 05/05/2024

create table persona (
	dni_persona serial not null primary key,
	primer_nombre varchar(20) not null,
	segundo_nombre varchar(20) null,
	ape_paterno varchar(20) not null,
	ape_materno varchar(20) not null
);

create table administrador (
	id_admin integer not null primary key references persona(dni_persona),
	email varchar(50) null
);

create table cliente (
	dni_cliente integer not null primary key references persona(dni_persona),
	historial_cretidicio text not null -- un link
);

-- Un cliente puede tener muchas cuentas de usuario.
create table usuario ( 
	num_movil char(9) not null primary key,
	dni_usuario integer not null references cliente(dni_cliente),
	fecha_inicio date not null, 
	observaciones text null
);

create table cuenta_usuario (
	num_movil_usuario char(9) not null primary key references usuario(num_movil),
	saldo decimal(10, 5) not null,
	ip_cuenta_usuario varchar(32) not null,
	contraseña varchar(10) not null,
	limite_por_dia decimal(10,3) not null,
	email varchar(50) null,
	estado_de_actividad bool -- Por si esta suspendido.
);

create table departamento (
	id_departamento serial not null primary key,
	nombre_departamento varchar(50) not null,
	descripcion text null
);

create table provincia ( 
	id_departamento integer not null references departamento(id_departamento),
	id_provincia serial not null primary key,
	nombre_provincia varchar(50) not null,
	descripcion text null
);

create table distrito (
	id_distrito serial not null primary key,
	id_provincia integer not null references provincia(id_provincia),
	nombre_distrito varchar(50) not null,
	descripcion text null
);

create table direccion_usuario (
	num_movil_usuario char(9) not null primary key references usuario(num_movil),
	direcccion_exacta varchar(100) not null,
   	id_departamento integer not null references departamento(id_departamento),
	id_provincia integer not null references provincia(id_provincia),
	id_distrito integer not null references distrito(id_distrito)
);

-- Por el momento tiene todos los permisos.
-- Solo existe una cuenta administrador.
create table cuenta_admin (
	id_cuenta_admin integer not null primary key references administrador(id_admin),
	ip_cuenta_admin varchar(32) not null,
	contraseña varchar(10) not null,
	estado_de_actividad bool not null
);

-- Aqui esta el regristo de la operación
create table operacion ( 
	id_operacion serial not null primary key,
	cuenta_origen char(9) not null references cuenta_usuario(num_movil_usuario),
	cuenta_destino char(9) not null references cuenta_usuario(num_movil_usuario),
	hora_operacion time not null,
	fecha_operacion date not null,
	monto_operacion decimal(10, 3) not null
);

create table tarjeta_usuario (
	num_tarjeta varchar(20) not null primary key,
	num_movil_usuario char(9) not null references usuario(num_movil),
	fecha_vencimiento date not null,
	codigo_csv varchar(3) not null
);

create table registro_recarga (
	id_registro_recarga serial not null primary key,
	-- A que cuenta va dirigida la recarga?
	-- Sale de los datos de la tarjeta: 2 campo, por cada usuario o cuenta que lo use se crea una nueva tabla.
	-- Uno de los dos tiene que rellenarse, no pueden ser nulos los dos
	num_tarjeta varchar(20)  null references tarjeta_usuario(num_tarjeta), -- Primer método de recarga
	codigo_generado varchar(30) null, -- Segundo método de recarga
	monto decimal(3,3) not null,
	fecha date not null,
	hora time not null,
	estado_recarga varchar(20) not null -- Si esta en proceso o se consolido la recarga.   
);

create table acceso_empleado (
	id_acceso serial not null primary key,
	num_movil_empleador char(9) not null references cuenta_usuario(num_movil_usuario),
	num_movil_empleado char(9) not null references cuenta_usuario(num_movil_usuario),
	estado_acceso bool not null, -- si aun tiene el acceso.
	fecha_acceso date not null,
	unique(num_movil_empleador, num_movil_empleado)
);
