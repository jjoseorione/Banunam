package mx.unam.banunam.repository;


import mx.unam.banunam.system.model.CuentaDebito;
import mx.unam.banunam.system.model.TarjetaDebito;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.CuentaDebitoRepository;
import mx.unam.banunam.system.repository.TarjetaDebitoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar tests básicos de la entidad TarjetaDebito, así como de las consultas
 * heredadas de CrudRepository a TarjetaDebitoRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class TarjetaDebitoEntityTests {
    @Autowired
    private TarjetaDebitoRepository tarjetaDebitoRepository;
    @Autowired
    private CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 1;
    private final Integer NO_CUENTA = 10000000;
    private final String NO_TDD_C = "1709458877631248";
    private final String NO_TDD_R = "1709632515478587";

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
    @DisplayName(value = "Buscar todas las TDDs")
    @Test
    void findAllTarjetasDebito(){
        System.out.println("Buscar todas las tarjetas de crédito");
        tarjetaDebitoRepository.findAll().forEach(System.out::println);
    }

    //@Commit
    @Transactional
    @DisplayName(value = "Crear TDD y asignar a una cuenta")
    @Test
    void createTarjetaDebitoYAsignarACuenta(){
        System.out.println("Crear tarjeta de crédito y asignar a una cuenta");
        Optional<CuentaDebito> cuentaDebito = cuentaDebitoRepository.findById(NO_CUENTA);
        if(cuentaDebito.isPresent()){
            System.out.println("Cuenta: " + cuentaDebito.get());
            TarjetaDebito tarjetaDebito = new TarjetaDebito(NO_TDD_C, 'E', cuentaDebito.get(), LocalDate.of(2025, 7, 12), null, null);
                TarjetaDebito.builder().noTarjeta(NO_TDD_C).fisicaElectronica('E').cuentaDebito(cuentaDebito.get()).fechaExp(LocalDate.now().plusYears(5))
                                .cvv(null).estatus(null).build();
            tarjetaDebitoRepository.save(tarjetaDebito);
            System.out.println(tarjetaDebito);
        }
    }

    @Transactional
    @DisplayName(value = "Buscar TDD por número de TDD")
    @Test
    void findTarjetaDebitoByNoTarjeta(){
        System.out.println("Buscar TDD por número de tarjeta " + NO_TDD_R);
        tarjetaDebitoRepository.findById(NO_TDD_R).ifPresent(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Buscar TDD por noCuenta")
    @Test
    void findTarjetaDebitoByNoCuenta(){
        final Integer NO_CUENTA = 100000002;
        System.out.println("Buscar TDD por noCuenta");
        List<TarjetaDebito> tdds = tarjetaDebitoRepository.findByCuentaDebitoNoCuenta(10000002);
        Assertions.assertFalse(tdds.isEmpty());
        tdds.forEach(tarjetaDebito ->
            System.out.println(tarjetaDebito + " " + tarjetaDebito.getCuentaDebito().getCliente())
        );
    }
}
