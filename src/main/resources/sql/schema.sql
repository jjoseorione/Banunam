DROP DATABASE IF EXISTS banunam;
CREATE DATABASE banunam;
SET NAMES utf8mb4;
ALTER DATABASE banunam CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

USE banunam;

CREATE TABLE estados(
    id_estado INT UNSIGNED NOT NULL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    abreviatura CHAR(3) NOT NULL UNIQUE
);

CREATE TABLE municipios(
    id_municipio INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    cve_municipio INT UNSIGNED NOT NULL,
    id_estado INT UNSIGNED NOT NULL,

    UNIQUE(id_estado, cve_municipio),
    CONSTRAINT fk_estados_municipios FOREIGN KEY(id_estado) REFERENCES estados(id_estado)
);

CREATE TABLE colonias(
    id_colonia INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    cp CHAR(5) NOT NULL,
    id_municipio INT UNSIGNED NOT NULL,

    CONSTRAINT fk_municipios_colonias FOREIGN KEY (id_municipio) REFERENCES municipios(id_municipio)
);

CREATE TABLE clientes(
	no_cliente INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	apellido_1 VARCHAR(50) NOT NULL,
	apellido_2 VARCHAR (50) NOT NULL DEFAULT '',
	rfc CHAR(13) UNIQUE NOT NULL,
	fecha_nac DATE NOT NULL,
	correo VARCHAR(50) NOT NULL UNIQUE,
	contrasena VARCHAR(100) NOT NULL,
	telefono VARCHAR(20) NOT NULL,

	--Se valida que el cliente sea mayor de edad
	--CONSTRAINT check_validaEdad CHECK (fechaNac < DATE_SUB(NOW(), INTERVAL 18 YEAR)),
	CONSTRAINT check_validaCorreo CHECK (correo LIKE '%@%')
);

CREATE TABLE domicilios(
    id_domicilio INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    calle VARCHAR(100) NOT NULL,
    num_interior VARCHAR(30) NULL,
    num_exterior VARCHAR(30) NOT NULL,
    no_cliente INT UNSIGNED NOT NULL UNIQUE,
    id_colonia INT UNSIGNED NOT NULL,

    CONSTRAINT fk_colonias_domicilio FOREIGN KEY(id_colonia) REFERENCES colonias(id_colonia),
    CONSTRAINT fk_clientes_domicilio FOREIGN KEY(no_cliente) REFERENCES clientes(no_cliente)
);

CREATE TABLE cuentas_debito(
	no_cuenta INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	no_cliente INT UNSIGNED NOT NULL UNIQUE,
	saldo DECIMAL(8,2) NOT NULL DEFAULT 0,

	CONSTRAINT fk_cliente_cuentaDebito FOREIGN KEY(no_cliente) REFERENCES clientes (no_cliente)
);
ALTER TABLE cuentas_debito AUTO_INCREMENT = 10000000;

CREATE TABLE cuentas_credito(
	no_cuenta INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	lim_credito DECIMAL(8,2) NOT NULL DEFAULT 0,
	no_cliente INT UNSIGNED NOT NULL UNIQUE,
	saldo_utilizado DECIMAL(8,2) NOT NULL DEFAULT 0,
	tasa_interes_anual DECIMAL(4,2) UNSIGNED NOT NULL DEFAULT 0,

	CONSTRAINT fk_cliente_cuentaCredito FOREIGN KEY(no_cliente) REFERENCES clientes (no_cliente)
);
ALTER TABLE cuentas_credito AUTO_INCREMENT = 20000000;

CREATE TABLE cuentas_prestamo(
	no_cuenta INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	no_cliente INT UNSIGNED NOT NULL,
	monto_solicitado DECIMAL(8,2) UNSIGNED NOT NULL,
	no_periodos TINYINT UNSIGNED NOT NULL,
	periodicidad CHAR(1) NOT NULL,
	tasa_interes_anual DECIMAL(4,2) UNSIGNED NOT NULL,
	periodo_activo TINYINT UNSIGNED NOT NULL DEFAULT 1,
	saldo_restante DECIMAL(8,2) UNSIGNED NOT NULL,
	fecha_aprobacion DATE NOT NULL,

	CONSTRAINT fk_cliente_cuentaPrestamo FOREIGN KEY(no_cliente) REFERENCES clientes (no_cliente)
);
ALTER TABLE cuentas_prestamo AUTO_INCREMENT = 30000000,
	ADD CONSTRAINT CHK_periodicidad CHECK (periodicidad = 'Q' OR periodicidad = 'M');

CREATE TABLE tarjetas_debito(
	no_tarjeta VARCHAR(16) NOT NULL PRIMARY KEY,
	fisica_electronica CHAR(1) NOT NULL,
	no_cuenta INT UNSIGNED NOT NULL,
	fecha_exp DATE NOT NULL,
	cvv SMALLINT(3) UNSIGNED NULL,
	estatus CHAR(1) NOT NULL DEFAULT 'N',

    UNIQUE (no_cuenta, fisica_electronica),
	CONSTRAINT fk_cuentaDebito_tarjetaDebito FOREIGN KEY(no_cuenta) REFERENCES cuentas_debito (no_cuenta),
    CONSTRAINT check_noTarjeta CHECK (no_tarjeta LIKE '1709%')
);
ALTER TABLE tarjetas_debito
	ADD CONSTRAINT CHK_estatus CHECK (estatus = 'N' OR estatus = 'A' OR estatus = 'I' OR estatus = 'B' OR estatus = 'E');
	--NUEVA, ACTIVA, INACTIVA, BLOQUEADA, EXPIRADA

CREATE TABLE tarjetas_credito(
	no_tarjeta VARCHAR(16) NOT NULL PRIMARY KEY,
	fisica_electronica CHAR(1) NOT NULL,
	adicional TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	no_cuenta INT UNSIGNED NOT NULL,
	fecha_exp DATE NOT NULL,
	cvv SMALLINT(3) UNSIGNED NULL,
	estatus CHAR(1) NOT NULL DEFAULT 'N',

    UNIQUE (no_cuenta, fisica_electronica, adicional),
	CONSTRAINT fk_cuentaCredito_tarjetaCredito FOREIGN KEY(no_cuenta) REFERENCES cuentas_credito (no_cuenta),
	CONSTRAINT check_noTarjeta CHECK (no_tarjeta LIKE '1202%')
);

CREATE TABLE tipos_movimiento(
	tipo_mov TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(100) NOT NULL UNIQUE,
	signo_debito CHAR(1),
	tipo_cuenta CHAR(1) NOT NULL
	--signoDebito significa que tendrá el signo real. Es decir, si es deuda, es '-'. Si es saldo a favor, es '+'
	--Cuando el movimiento se aplique a la cuenta del cliente, sumará o restará según el signo y la cuenta. Si la
	--cuenta es de débito, un signo '-' debe restar al saldo y un signo '+' debe sumar, ya que el saldo de la cuenta
	 --es a favor. Si la cuenta es de crédito o préstamo, un signo '-' debe sumar al saldo y un signo '+' debe restar.
	 --La columna productoMovimiento indica si es una operación asociada a una cuenta de débito, crédito o de préstamo
);
ALTER TABLE tipos_movimiento
	ADD CONSTRAINT CHK_signoDebito CHECK (signo_debito = '+' OR signo_debito = '-');
ALTER TABLE tipos_movimiento
	ADD CONSTRAINT CHK_tipoCuenta CHECK (tipo_cuenta = 'D' OR tipo_cuenta = 'C' OR tipo_cuenta = 'P');
	--Se asegura que el movimiento pertenezca al contexto de Débito, Crédito o Préstamo


CREATE TABLE origen_destino_movimientos(
	tipo_origen_destino TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(40) NOT NULL UNIQUE
	--La tabla origen_destino_movimientos guarda el origen o destino de un movimiento. Estas opciones
	--pueden ser un comercio (en caso de que se trate de una compra), otra cuenta de crédito/débito en caso de una
	--transferencia, un cajero (ventanilla) o un cajero automático. --
);

CREATE TABLE movimientos_debito(
	folio BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	timestamp_mov DATETIME NOT NULL,
	monto DECIMAL(8,2) UNSIGNED NOT NULL,
	tipo_mov TINYINT UNSIGNED NOT NULL,
	no_cuenta INT UNSIGNED NOT NULL,
	origen_destino VARCHAR(30) NOT NULL,
	tipo_origen_destino TINYINT UNSIGNED NOT NULL,
	concepto VARCHAR(50) NULL,


	CONSTRAINT fk_tipoMovimiento_movimientosDebito FOREIGN KEY (tipo_mov) REFERENCES tipos_movimiento (tipo_mov),
	CONSTRAINT fk_cuentaDebito_movimientosDebito FOREIGN KEY (no_cuenta) REFERENCES cuentas_debito (no_cuenta),
	CONSTRAINT fk_origenDestinoMovimientos_movimientosDebito FOREIGN KEY (tipo_origen_destino) REFERENCES origen_destino_movimientos (tipo_origen_destino)
);

CREATE TABLE movimientos_credito(
	folio BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	timestamp_mov DATETIME NOT NULL,
	monto INT UNSIGNED NOT NULL,
	tipo_mov TINYINT UNSIGNED NOT NULL,
	no_cuenta INT UNSIGNED NOT NULL,
	origen_destino VARCHAR(30) NOT NULL,
	tipo_origen_destino TINYINT UNSIGNED NOT NULL,
	concepto VARCHAR(100) NULL,


	CONSTRAINT fk_tipoMovimiento_movimientosCredito FOREIGN KEY (tipo_mov) REFERENCES tipos_movimiento (tipo_mov),
	CONSTRAINT fk_cuentaCredito_movimientosCredito FOREIGN KEY (no_cuenta) REFERENCES cuentas_credito (no_cuenta),
	CONSTRAINT fk_origenDestinoMovimientos_movimientosCredito FOREIGN KEY (tipo_origen_destino) REFERENCES origen_destino_movimientos (tipo_origen_destino)
);

CREATE TABLE movimientos_prestamo(
	folio BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	timestamp_mov DATETIME NOT NULL,
	monto INT UNSIGNED NOT NULL,
	tipo_mov TINYINT UNSIGNED NOT NULL,
	no_cuenta INT UNSIGNED NOT NULL,
	origen_destino VARCHAR(30) NOT NULL,
	tipo_origen_destino TINYINT UNSIGNED NOT NULL,
	concepto VARCHAR(50) NULL,


	CONSTRAINT fk_tipoMovimiento_movimientosPrestamo FOREIGN KEY (tipo_mov) REFERENCES tipos_movimiento (tipo_mov),
	CONSTRAINT fk_cuentaPrestamo_movimientosPrestamo FOREIGN KEY (no_cuenta) REFERENCES cuentas_prestamo (no_cuenta),
	CONSTRAINT fk_origenDestinoMovimientos_movimientosPrestamo FOREIGN KEY (tipo_origen_destino) REFERENCES origen_destino_movimientos (tipo_origen_destino)
);

CREATE TABLE tipos_usuario(
	tipo_usuario TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	alias VARCHAR(10) UNIQUE NOT NULL,
	descripcion VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE usuarios(
	id_usuario SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	usuario VARCHAR(7) NOT NULL UNIQUE,
	nombre VARCHAR(50) NOT NULL,
	apellido_1 VARCHAR(50) NOT NULL,
	apellido_2 VARCHAR (50) NOT NULL DEFAULT '',
	correo VARCHAR(50) NOT NULL UNIQUE,
	contrasena VARCHAR(100) NOT NULL,
	tipo_usuario TINYINT UNSIGNED NOT NULL,
	intentos TINYINT UNSIGNED NOT NULL,
	estatus CHAR(1) NOT NULL,
	fecha_exp_usuario DATE NOT NULL,
	fecha_exp_contrasena DATE NOT NULL,

	CONSTRAINT fk_tiposUsuario_usuarios FOREIGN KEY (tipo_usuario) REFERENCES tipos_usuario (tipo_usuario)
);

ALTER TABLE usuarios
	ADD CONSTRAINT CHK_estatus CHECK (estatus = 'A' OR estatus = 'B' OR estatus = 'I');