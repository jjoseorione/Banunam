--######################################### MÓDULO CLIENTE
-----------------Catálogos

--Tabla: estados
--id_estado, nombre, abreviatura
INSERT INTO estados VALUES (1, "Aguascalientes", "AGU");
INSERT INTO estados VALUES (2, "Baja California", "BCN");
INSERT INTO estados VALUES (3, "Baja California Sur", "BCS");
INSERT INTO estados VALUES (4, "Campeche", "CAM");
INSERT INTO estados VALUES (5, "Chiapas", "CHP");

--Tabla: municipios
--id_municipio, nombre, cve_municipio, id_estado
INSERT INTO municipios VALUES (NULL, "Aguascalientes", 1, 1);
INSERT INTO municipios VALUES (NULL, "Asientos", 2, 1);
INSERT INTO municipios VALUES (NULL, "Calvillo", 3, 1);
INSERT INTO municipios VALUES (NULL, "Jesus María", 4, 1);
INSERT INTO municipios VALUES (NULL, "Rincón de Romos", 5, 1);

--Tabla: colonias
--id_colonia, nombre, cp, id_municipio
INSERT INTO colonias VALUES (NULL, "Aguascalientes Centro", "20000", 1);
INSERT INTO colonias VALUES (NULL, "Colinas del Río", "20010", 1);
INSERT INTO colonias VALUES (NULL, "Olivares Santana", "20010", 1);
INSERT INTO colonias VALUES (NULL, "Las Brisas", "20010", 1);
INSERT INTO colonias VALUES (NULL, "Ramón Romo Franco", "20010", 1);

--Tipos de Movimiento
--tipoMovimiento, Descripción, signoDebito, tipoCuenta
INSERT INTO tipos_movimiento VALUES(NULL, 'Depósito de Nómina', '+', 'D');
INSERT INTO tipos_movimiento VALUES(NULL, 'Transferencia recibida', '+', 'D');
INSERT INTO tipos_movimiento VALUES(NULL, 'Depósito recibido', '+', 'D');
INSERT INTO tipos_movimiento VALUES(NULL, 'Transferencia realizada', '-', 'D');
INSERT INTO tipos_movimiento VALUES(NULL, 'Compra con TDD en negocio', '-', 'D');
INSERT INTO tipos_movimiento VALUES(NULL, 'Retiro de efectivo', '-', 'D');

INSERT INTO tipos_movimiento VALUES(NULL, 'Pago TDC', '+', 'C');
INSERT INTO tipos_movimiento VALUES(NULL, 'Transferencia realizada (disposición)', '-', 'C');
INSERT INTO tipos_movimiento VALUES(NULL, 'Compra con TDC en negocio', '-', 'C');
INSERT INTO tipos_movimiento VALUES(NULL, 'Disposición de efectivo', '-', 'C');

INSERT INTO tipos_movimiento VALUES(NULL, 'Aprobación de Préstamo', '-', 'P');
INSERT INTO tipos_movimiento VALUES(NULL, 'Pago de recibo', '+', 'P');

--origen_destino_movimientos

INSERT INTO origen_destino_movimientos VALUES (NULL, 'Cuenta débito empresarial');
INSERT INTO origen_destino_movimientos VALUES (NULL, 'Cuenta débito particular');
INSERT INTO origen_destino_movimientos VALUES (NULL, 'Cuenta crédito');
INSERT INTO origen_destino_movimientos VALUES (NULL, 'Cuenta préstamo');
INSERT INTO origen_destino_movimientos VALUES (NULL, 'Comercio');
INSERT INTO origen_destino_movimientos VALUES (NULL, 'Cajero Automático / Practicaja');
INSERT INTO origen_destino_movimientos VALUES (NULL, 'Cajero / Taquilla');


-----------------Inserción de CLIENTES
---------------------------noCliente, nombre, apPat, apMat, rfc, fechaNac, email, contrasena, telefono
--Cliente 1 (Sólo cliente)
INSERT INTO clientes VALUES(NULL, 'Francisco', 'Sánchez', 'Hinojosa', 'FSH940508', '1994-05-08', 'francisco.hinojosa@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5545812578');
--Cliente 2 (Cliente y cuentas)
INSERT INTO clientes VALUES(NULL, 'Suleima', 'Álvarez', 'Granados', 'SAG851213', '1985-12-13', 'suleima.alvarez@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5578412569');
--Cliente 3 (Completo)
INSERT INTO clientes VALUES(NULL, 'Ramiro', 'Solares', 'Fuentes', 'RSF870224', '1987-02-24', 'ramiro.solares@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5548796325');
--Cliente 4 (Completo sólo débito)
INSERT INTO clientes VALUES(NULL, 'Juan Carlos', 'Olivares', 'Olalde', 'JOO780301', '1978-03-01', 'juancarlos.olivares@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5678258965');
--Cliente 5 (Completo sólo débito)
INSERT INTO clientes VALUES(NULL, 'Alfonso', 'Fuentes', 'de la Oca', 'AFO651214', '1965-12-14', 'alfonso.fuentes@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5696154785');
--Cliente 6 (Completo débito y crédito)
INSERT INTO clientes VALUES(NULL, 'Selene', 'Samariego', 'Vergara', 'SSV960430', '1996-04-30', 'selene.samariego@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5521023058');
--Cliente 7 (Completo débito y crédito)
INSERT INTO clientes VALUES(NULL, 'Benito', 'López', 'Fernandez', 'BLF941109', '1994-11-09', 'benito.lopez@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5509976632');
--Cliente 8 (Completo débito y préstamo)
INSERT INTO clientes VALUES(NULL, 'Valentina', 'Rodríguez', 'Sepúlveda', 'VRS890528', '1989-05-28', 'valentina.rodriguez@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5602287810');
--Cliente 9 (Completo débito y préstamo)
INSERT INTO clientes VALUES(NULL, 'María Luisa', 'Álvarez', 'Villa', 'MAV011024', '2001-10-24', 'marialuisa.alvarez@gmail.com', '$2a$16$.Jzzn5XBRvHe2HMZIuvMouquHjNMXPs0NhhjFFt1S4hqr3XWRkCYa', '5633657884');

--domicilios
--id_domicilio, calle, num_interior, num_exterior, no_cliente, id_colonia
INSERT INTO domicilios VALUES(NULL, 'José María Morelos y Pavón', NULL, '515', 2, 1);
INSERT INTO domicilios VALUES(NULL, 'Gral Álvaro Obregón', '101', '204', 3, 1);
INSERT INTO domicilios VALUES(NULL, 'Pedro Parga', NULL, '137', 4, 1);
INSERT INTO domicilios VALUES(NULL, 'Condell', NULL, '101', 5, 1);
INSERT INTO domicilios VALUES(NULL, 'Melchor Ocampo', NULL, '113', 6, 1);
INSERT INTO domicilios VALUES(NULL, 'Hospitalidad', "2A", '135', 7, 1);
INSERT INTO domicilios VALUES(NULL, 'Gral. Ignacio Zaragoza', NULL, '414', 8, 1);
INSERT INTO domicilios VALUES(NULL, '5 de Mayo', NULL, '538', 9, 1);

-----------------Inserción de cuentas de debito (clientes 2 al 9)
---------------------------------noCuenta, noCliente, saldo
INSERT INTO cuentas_debito VALUES(NULL, 2, 15000.00);
INSERT INTO cuentas_debito VALUES(NULL, 3, 16000.00);
INSERT INTO cuentas_debito VALUES(NULL, 4, 784.25);
INSERT INTO cuentas_debito VALUES(NULL, 5, 1896.36);
INSERT INTO cuentas_debito VALUES(NULL, 6, 11854.23);
INSERT INTO cuentas_debito VALUES(NULL, 7, 183.25);
INSERT INTO cuentas_debito VALUES(NULL, 8, 1587.36);
INSERT INTO cuentas_debito VALUES(NULL, 9, 17893.35);

-----------------Inserción de tarjetas de débito (clientes 3 al 9)
----------------------------------noTarjeta, fisicaElectronica, noCuenta, fechaExp, cvv, estatus
INSERT INTO tarjetas_debito VALUES('1709632515478587', 'F', 10000001, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709632548588697', 'E', 10000001, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709685478525458', 'F', 10000002, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709100257896206', 'E', 10000002, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709300151987896', 'F', 10000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709624951098450', 'E', 10000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709651908409489', 'F', 10000004, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709362827000958', 'E', 10000004, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709144780520365', 'F', 10000005, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709366585479015', 'E', 10000005, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709368578963204', 'F', 10000006, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709636585200214', 'E', 10000006, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709778496351278', 'F', 10000007, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_debito VALUES('1709102547007963', 'E', 10000007, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');

-----------------Inserción de MOVIMIENTOS_DEBITO (clientes 3 al 9)
-------------------------------------folio, timestampMov, monto, tipoMovimiento, noCuenta, idOrigenDestino, tipoOrigenDestino, concepto

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000001, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000001, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000001, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000001, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000001, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000001, '45039852', 7, 'Gasto');       --Retiro de efectivo

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000002, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000002, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000002, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000002, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000002, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000002, '45039852', 7, 'Gasto');       --Retiro de efectivo

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000003, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000003, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000003, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000003, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000003, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000003, '45039852', 7, 'Gasto');       --Retiro de efectivo

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000004, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000004, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000004, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000004, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000004, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000004, '45039852', 7, 'Gasto');       --Retiro de efectivo

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000005, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000005, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000005, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000005, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000005, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000005, '45039852', 7, 'Gasto');       --Retiro de efectivo

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000006, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000006, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000006, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000006, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000006, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000006, '45039852', 7, 'Gasto');       --Retiro de efectivo

INSERT INTO movimientos_debito VALUES(NULL, NOW(), 7500.00, 1, 10000007, '31525852321', 1, 'Pago de Nómina');   --Depósito de nómina
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 480.0, 2, 10000007, '10000092', 2, 'Gracias amigo');       --Transferencia recibida
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 1500.0, 3, 10000007, '12345678', 7, 'Depósito en sucursal Buenavista');       --Depósito recibido
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 496.36, 4, 10000007, '10000090', 2, 'Lo que te debía');       --Transferencia realizada
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 899.89, 5, 10000007, '12345678', 5, 'Compra Stradivarius SUC012585');       --Compra con TDD en negocio
INSERT INTO movimientos_debito VALUES( NULL, NOW(), 3000.0, 6, 10000007, '45039852', 7, 'Gasto');       --Retiro de efectivo



-----------------Inserción de CUENTAS_CREDITO (clientes 2,3,6,7)
---------------------------------noCuenta, limCredito, noCliente, saldoUtilizado, tasaInteresAnual
INSERT INTO cuentas_credito VALUES(NULL, 15000.0, 2, 1563.02, 12.5);
INSERT INTO cuentas_credito VALUES(NULL, 18000.0, 3, 8604.36, 9.5);
INSERT INTO cuentas_credito VALUES(NULL, 15000.0, 6, 1496.25, 9.5);
INSERT INTO cuentas_credito VALUES(NULL, 18000.0, 7, 7569.36, 9.5);

-----------------Inserción de TARJETAS_CREDITO (clientes 3,6,7)
---------------------------------noTarjeta, fisicaElectronica, adicional, noCuenta, fechaExp, cvv, estatus
INSERT INTO tarjetas_credito VALUES('1202488996571001', 'F', 0, 20000001, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202488915854774', 'E', 0, 20000001, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202748512365715', 'F', 0, 20000002, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202417485253601', 'E', 0, 20000002, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202852456971302', 'F', 0, 20000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202748512332158', 'E', 0, 20000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
--Adicionales
INSERT INTO tarjetas_credito VALUES('1202748632058745', 'F', 1, 20000001, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202852471023657', 'F', 1, 20000002, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202987123457410', 'F', 2, 20000002, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202749467182937', 'F', 1, 20000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202390709584147', 'F', 2, 20000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');
INSERT INTO tarjetas_credito VALUES('1202748963880145', 'F', 3, 20000003, DATE_ADD(NOW(), INTERVAL 5 YEAR), NULL, 'A');

-----------------Inserción de MOVIMIENTOS_CREDITO (clientes 3,6,7)
---------------------------------folio, timestampMov, monto, tipoMovimiento, noCuenta, idOrigenDestino, tipoOrigenDestino, concepto
INSERT INTO movimientos_credito VALUES( NULL, NOW(), 16000, 7, 20000001, '10000001', 2, 'Su pago. Gracias.' );
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 8, 20000001, '10000002', 5, 'Comida de ayer');
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 9, 20000001, '0005696', 6, 'Pago con TDC Banunam Italianni\'s SA');
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 10, 20000001, '45039852', 7, '');

INSERT INTO movimientos_credito VALUES( NULL, NOW(), 16000, 7, 20000002, '10000001', 2, 'Su pago. Gracias.' );
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 8, 20000002, '10000002', 5, 'Comida de ayer');
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 9, 20000002, '0005696', 6, 'Pago con TDC Banunam Italianni\'s SA');
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 10, 20000002, '45039852', 7, '');

INSERT INTO movimientos_credito VALUES( NULL, NOW(), 16000, 7, 20000003, '10000001', 2, 'Su pago. Gracias.' );
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 8, 20000003, '10000002', 5, 'Comida de ayer');
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 9, 20000003, '0005696', 6, 'Pago con TDC Banunam Italianni\'s SA');
INSERT INTO movimientos_credito VALUES(NULL, NOW(), 850.90, 10, 20000003, '45039852', 7, '');


-----------------Inserción de CUENTAS_PRESTAMO (clientes 2,3,8,9)
---------------------------------noCuenta, noCliente, montoSolicitado, noPeriodos, periodicidad, tasaInteresAnual, periodoActivo, saldoRestante, fechaAprobacion
INSERT INTO cuentas_prestamo VALUES(NULL, 2, 36000.0, 24, 'Q', 16.85, 1, 36000.0, NOW());
INSERT INTO cuentas_prestamo VALUES(NULL, 3, 48000.0, 36, 'Q', 16.85, 1, 36000.0, NOW());
INSERT INTO cuentas_prestamo VALUES(NULL, 8, 50000.0, 24, 'M', 16.85, 1, 36000.0, NOW());
INSERT INTO cuentas_prestamo VALUES(NULL, 9, 63000.0, 36, 'M', 16.85, 1, 36000.0, NOW());

-----------------Inserción de MOVIMIENTOS_PRESTAMO (clientes 3,8,9)
---------------------------------------folio, timestampMov, monto, tipoMovimiento, noCuenta, idOrigenDestino, tipoOrigenDestino, concepto
INSERT INTO movimientos_prestamo VALUES( NULL, NOW(), 48000.0, 11, 30000001, '10000001', 2, 'Préstamo de nómina por $48000.0');
INSERT INTO movimientos_prestamo VALUES( NULL, NOW(), 1752.75, 12, 30000001, '10000001', 2, 'Pago de recibo 1');

INSERT INTO movimientos_prestamo VALUES( NULL, NOW(), 50000.0, 11, 30000002, '10000006', 2, 'Préstamo de nómina por $50000.0');
INSERT INTO movimientos_prestamo VALUES( NULL, NOW(), 1752.75, 12, 30000002, '10000006', 2, 'Pago de recibo 1');

INSERT INTO movimientos_prestamo VALUES( NULL, NOW(), 63000.0, 11, 30000003, '10000007', 2, 'Préstamo de nómina por $63000.0');
INSERT INTO movimientos_prestamo VALUES( NULL, NOW(), 1752.75, 12, 30000003, '10000007', 2, 'Pago de recibo 1');

--######################################### MÓDULO USUARIO
INSERT INTO tipos_usuario VALUES(NULL, 'ADMIN', 'Administrador del Sistema');
INSERT INTO tipos_usuario VALUES(NULL, 'EXEC', 'Ejecutivo Bancario');

INSERT INTO usuarios VALUES(NULL, 'XMX7265', 'Juan Carlos', 'Medina', 'González', 'juancarlos.medina@banunam.com', '$2a$11$cahGZAyWLpWypsZ3gBTuieJd7mOf4Pv7i9jlU.x91jgH4qaHpap3m', 1, 0, 'A',
    DATE_ADD(NOW(), INTERVAL 6 MONTH), DATE_ADD(NOW(), INTERVAL 1 MONTH));
INSERT INTO usuarios VALUES(NULL, 'ZPT0009', 'Roberto', 'Montoya', 'Payan', 'roberto.montoya@banunam.com', '$2a$11$cahGZAyWLpWypsZ3gBTuieJd7mOf4Pv7i9jlU.x91jgH4qaHpap3m', 2, 0, 'A',
    DATE_ADD(NOW(), INTERVAL 6 MONTH), DATE_ADD(NOW(), INTERVAL 1 MONTH));
