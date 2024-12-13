package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.TarjetaDebito;
import mx.unam.banunam.system.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar tests básicos de la entidad Cliente, así como de las consultas
 * heredadas de CrudRepository a ClienteRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class ClienteEntityTests{
    @Autowired
    ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 1;


    @BeforeEach
    void espaciado(){
        System.out.println();
        System.out.println("••••••••••••••••••••••••••••••");
    }
    @AfterEach
    void espaciadof(){
        System.out.println("••••••••••••••••••••••••••••••");
        System.out.println();
    }

    @DisplayName(value = "Mostrar todos los clientes")
    @Test
    void findAllClientesTest(){
        System.out.println("Buscar todos los clientes en la BBDD");
        System.out.println("Total de clientes en la BBDD: " + clienteRepository.count());
        clienteRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Buscar una clientes por noCliente")
    @Test
    void findClienteByNoClienteTest(){
        System.out.println("Buscar clientes por noCliente " + NO_CLIENTE);
        Optional<Cliente> optional = clienteRepository.findById(NO_CLIENTE);
        optional.ifPresent(System.out::println);
    }

    @DisplayName(value = "Crear nuevo clientes")
    @Test
    void createCliente(){
        System.out.println("Crear nuevo clientes");
//        Cliente clientes = new Cliente(null, "Pedro", "Solano", "Ruíz", "PSR871205", LocalDate.of(1987, 12, 5),
//                "pedro.solano@gmail.com", "t3mp0r4l", "5548781205", null, null);
        Cliente cliente = Cliente.builder().noCliente(null).nombre("Pedro").apellido1("Solano").apellido2("Ruiz").rfc("PSRE871205123").fechaNac(LocalDate.of(1987, 12, 5))
                .correo("pedro.solano@gmail.com").contrasena("t3mp0r4l").telefono("5548781205").cuentaDebito(null).cuentaDebito(null).cuentaPrestamo(null).build();
        clienteRepository.save(cliente);
        System.out.println("El nuevo clientes es: " + cliente);
    }

    @DisplayName(value = "Encontrar por num TDD")
    @Test
    void findClienteByNoTDD(){
        System.out.println("Encontrar clientes por num TDD");
        final String NO_TARJETA = "1709632515478587";
        final Integer NO_CLIENTE_ESPERADO = 3;
        Cliente cliente = clienteRepository.findByNoTarjetaDebito(NO_TARJETA).orElse(null);
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(NO_CLIENTE_ESPERADO ,cliente.getNoCliente());
        System.out.println(cliente);
    }


}
