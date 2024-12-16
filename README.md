# Banunam

## Descripción

Banunam es un proyecto que simula ser tres aplicaciones web distintas para el banco ficticio "Banunam", el cual está destinado a ser la primera opción bancaria de los estudiantes de la UNAM y extensible a estudiantes de más universidades públicas. Este banco sin ánimos de lucro permitirá a los estudiantes tener acceso a cuentas de ahorro, así como cuentas de crédito y débito a un muy bajo interés.

Las tres aplicaciones web que están contenidas dentro del proyecto son las siguientes.

 - Banca en línea: Aplicación en la que los clientes pueden acceder a la información de sus cuentas como movimientos, número de cuenta, saldo, etc.
 - Customer Care Center: Aplicación en la que los ejecutivos bancarios tienen acceso a la información de clientes, así como funcionalidades de apoyo como retiros y depósitos de efectivo, afiliación de clientes, creación de cuentas, etc.
 - User Administration: Aplicación en la que los administradores de usuario pueden acceder a la información de los usuarios (tanto ejecutivos como administradores), además de realizar actividades de administración como creaciones, ediciones, desbloqueos o borrados.

## Guía de instalación

El proyecto requiere la utilización del siguiente software:

 - Java 17+
- MariaDB 10.6+
- Maven 3.8+

Se debe descargar/clonar el proyecto. Posteriormente se debe modificar el archivo src/main/resources/application.yaml de la siguiente manera:

 - En la propiedad datasource.username se debe escribir el nombre del usuario a utilizar en su base de datos.
 - En la propuedad datasource.password se debe escribir la contraseña del usuario que está utilizando
 - En la propiedad datasource.url se debe verificar que el puerto al que apunta su base de datos sea el correcto.
 - En las propiedades jwt.secretUser y jwt.secretClient debe agregar sus propios secretos.

El poyecto contiene ya una base de datos de prueba, la cual contiene la información mínima suficiente para navegar a través de sus funciones. Dicha información se puede consultar en la el archivo src/main/resources/data.sql, donde se puede consultar los datos de inicio de sesión de los usuarios. El esquema de base de datos se puede encontrar en el archivo src/main/resources/data.sql. 

Para cargar la información debe crear una base de datos con el nombre Banunam (o en su defecto, usar el nombre que prefiera y cambiarlo en el application.yaml). Posteriormente debe ejecutar la clase src/test/java/unam/mx/banunam/BanunamApplicationTests. La ejecución de esta clase cargará toda la información de prueba a su base de datos para que pueda utilizar la aplicación.

## Utilización

El aplicativo cuenta con una iterfaz amigable e intuitiva. Para entrar en cada uno de los módulos se debe ingresar a las siguientes URLs:

 - Módulo de clientes: [localhost:8080/](http://localhost:8080/)
 - Módulo de ejecutivos: [http://localhost:8080/customer-care-center](http://localhost:8080/customer-care-center)
 - Módulo de adminiatración de usuarios: http://localhost:8080/user-administration
 
 En cada uno de los módulos se le solicitará loguearse para poder ingresar al aplicativo. La información de inicio de sesión de cada módulo es la siguiente:
 - Módulo de clientes: 1709632515478587
 - Módulo de ejecutivos: ZPT0009
 - Módulo de administración: XMX7265

La contraseña para todos es "t3mp0r4l". Dicha contraseña se encuentra cifrada en BBDD, por lo que no es posible obtenerla directamente en el archivo data.sql.

Navegue por cada uno de los módulos.
