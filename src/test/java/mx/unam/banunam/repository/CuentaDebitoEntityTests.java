package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.CuentaDebito;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.CuentaDebitoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar tests básicos de la entidad CuentaDebito, así como de las consultas
 * heredadas de CrudRepository a ClienteRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class CuentaDebitoEntityTests {
    @Autowired
    private CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 1;
    private final Integer NO_CUENTA = 10000000;

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

    @Transactional
    @DisplayName(value = "Buscar todas las cuentas de débito")
    @Test
    void findAllCuentasDebito(){
        System.out.println("Buscar todas las cuentas de débito");
        cuentaDebitoRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Crear cuenta de débito y asignarla a clientes")
    @Test
    void createCuentaDebitoYAsignarACliente(){
        System.out.println("Crear cuenta de débito y asignarla a clientes");
        Optional<Cliente> cliente = clienteRepository.findById(NO_CLIENTE);
        if(cliente.isPresent()){
            System.out.println("Cliente: " + cliente.get());
            CuentaDebito cuentaDebito =
                CuentaDebito.builder().noCuenta(null).cliente(cliente.get()).saldo(BigDecimal.ZERO).build();
            cuentaDebitoRepository.save(cuentaDebito);
            System.out.println("Cuenta creada " + cuentaDebito);
        }
    }

    @Transactional
    @DisplayName(value = "Buscar cuenta debito por noCuenta")
    @Test
    void findCuentaDebitoByNoCuenta(){
        System.out.println("Buscar cuenta por noCuenta " + NO_CUENTA);
        cuentaDebitoRepository.findById(NO_CUENTA).ifPresent(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Buscar clientes por noCuenta debito")
    @Test
    void findClienteByCuentaDebito(){
        System.out.println("Buscar clientes por cuenta de debito " + NO_CUENTA);
        Cliente cliente = cuentaDebitoRepository.findById(NO_CUENTA).get().getCliente();
        System.out.println(cliente);
    }


}
