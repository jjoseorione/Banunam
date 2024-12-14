package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.CuentaPrestamo;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.CuentaPrestamoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar tests básicos de la entidad CuentaDebito, así como de las consultas
 * heredadas de CrudRepository a ClienteRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class CuentaPrestamoEntityTests {
    @Autowired
    private CuentaPrestamoRepository cuentaPrestamoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 1;
    private final Integer NO_CUENTA = 30000000;

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
    @DisplayName(value = "Buscar todas las cuentas de préstamo")
    @Test
    void findAllCuentasPrestamo(){
        System.out.println("Buscar todas las cuentas de préstamo");
        cuentaPrestamoRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Crear cuenta de préstamo y asignarla a clientes")
    @Test
    void createCuentaPrestamoYAsignarACliente(){
        System.out.println("Crear cuenta de préstamo y asignarla al clientes");
        Optional<Cliente> cliente = clienteRepository.findById(NO_CLIENTE);
        if(cliente.isPresent()){
            System.out.println("Cliente: " + cliente.get());
            CuentaPrestamo cuentaPrestamo =
                CuentaPrestamo.builder().noCuenta(null).cliente(cliente.get()).montoSolicitado(BigDecimal.ZERO).noPeriodos(24)
                    .periodicidad('Q').tasaInteresAnual(16.5).periodoActivo(1).saldoRestante(BigDecimal.valueOf(32000.0)).fechaAprobacion(LocalDate.now()).build();
            cuentaPrestamoRepository.save(cuentaPrestamo);
            System.out.println(cuentaPrestamo);
        }
    }

    @Transactional
    @DisplayName(value = "Buscar cuenta de préstamo por noCuenta")
    @Test
    void findCuentaPrestamoByNoCuenta(){
        System.out.println("Buscar cuenta de préstamo por noCuenta " + NO_CUENTA);
        cuentaPrestamoRepository.findById(NO_CUENTA). ifPresent(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Buscar clientes por noCuenta préstamo")
    @Test
    void findClienteByCuentaPrestamo(){
        System.out.println("Buscar clientes por cuenta de préstamo " + NO_CUENTA);
        Cliente cliente = cuentaPrestamoRepository.findById(NO_CUENTA).get().getCliente();
        System.out.println(cliente);
    }
}
