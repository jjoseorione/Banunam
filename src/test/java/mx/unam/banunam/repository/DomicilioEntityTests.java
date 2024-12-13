package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.Colonia;
import mx.unam.banunam.system.model.Domicilio;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.ColoniaRepository;
import mx.unam.banunam.system.repository.DomicilioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class DomicilioEntityTests {
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ColoniaRepository coloniaRepository;

    private final Integer DOMICILIO = 3;
    private final Integer CLIENTE = 1;
    private final Integer COLONIA = 1;

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

    @DisplayName(value = "Mostrar todos los domicilios")
    @Test
    @Transactional
    void findAllDomicilios(){
        System.out.println("Mostrar todos los domicilios");
        domicilioRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Buscar domicilio por id")
    @Test
    @Transactional
    void findDomicilioById(){
        System.out.println("Buscar domicilio por id: " + DOMICILIO);
        Optional<Domicilio> domicilio = domicilioRepository.findById(DOMICILIO);
        domicilio.ifPresent(System.out::println);
        Assertions.assertEquals("Pedro Parga", domicilio.get().getCalle());
    }

    @DisplayName(value = "Crear domicilio y asignarlo a clientes")
    @Test
    @Transactional
    void createDomicilio(){
        System.out.println("Crear domicilio y asignarlo a clientes");
        Optional<Cliente> cliente = clienteRepository.findById(CLIENTE);
        Optional<Colonia> colonia = coloniaRepository.findById(COLONIA);
        Assertions.assertTrue(cliente.isPresent());
        Assertions.assertTrue(colonia.isPresent());

        System.out.println("Cliente: " + cliente.get());
        System.out.println("Colonia: " + colonia.get());
        Domicilio domicilio = Domicilio.builder()
                .calle("Prueba de creación de domicilio")
                .numExterior("99-A")
                .cliente(cliente.get())
                .colonia(colonia.get())
                .build();
        domicilioRepository.save(domicilio);
        System.out.println(domicilio);
    }

    @DisplayName(value = "Buscar domicilio por noCliente")
    @Test
    @Transactional
    void findDomicilioByNoCliente(){
        final int NO_CLIENTE = 4;
        System.out.println("Buscar domicilio por noCliente: " + NO_CLIENTE);
        Optional<Domicilio> domicilio = domicilioRepository.findByClienteNoCliente(NO_CLIENTE);
        domicilio.ifPresent(System.out::println);
        Assertions.assertEquals("Pedro Parga", domicilio.get().getCalle());
    }
}
