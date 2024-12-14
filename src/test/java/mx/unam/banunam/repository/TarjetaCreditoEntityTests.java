package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.CuentaCredito;
import mx.unam.banunam.system.model.TarjetaCredito;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.CuentaCreditoRepository;
import mx.unam.banunam.system.repository.TarjetaCreditoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar tests básicos de la entidad TarjetaCredito, así como de las consultas
 * heredadas de CrudRepository a TarjetaCreditoRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class TarjetaCreditoEntityTests {
    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;
    @Autowired
    private CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 2;
    private final Integer NO_CUENTA = 20000000;
    private final String NO_TDC_C = "1202458877631248";
    private final String NO_TDC_R = "1202488996571001";

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
    @DisplayName(value = "Buscar todas las tarjetas de crédito")
    @Test
    void findAllTarjetasCredito(){
        System.out.println("Buscar todas las tarjetas de crédito");
        tarjetaCreditoRepository.findAll().forEach(System.out::println);
    }

    @Commit
    @Transactional
    @DisplayName(value = "Crear tarjeta de crédito y asignar a una cuenta")
    @Test
    void createTarjetaCreditoYAsignarACuenta(){
        System.out.println("Crear tarjeta de crédito y asignar a una cuenta");
        Optional<CuentaCredito> cuentaCredito = cuentaCreditoRepository.findById(NO_CUENTA);
        if(cuentaCredito.isPresent()){
            System.out.println("Cuenta: " + cuentaCredito.get());
            TarjetaCredito tarjetaCredito =
                TarjetaCredito.builder().noTarjeta(NO_TDC_C).fisicaElectronica('F').adicional(null).cuentaCredito(cuentaCredito.get())
                        .fechaExp(LocalDate.now().plusYears(5)).estatus('N').build();
            tarjetaCreditoRepository.save(tarjetaCredito);
            System.out.println("Tarjeta de crédito creada: ");
            System.out.println(tarjetaCredito);
        }
    }

    @Transactional
    @DisplayName(value = "Buscar tdc por número de TDC")
    @Test
    void findTarjetaCreditoByNoTarjeta(){
        System.out.println("Buscar tarjeta de crédito por número de tarjeta " + NO_TDC_R);
        tarjetaCreditoRepository.findById(NO_TDC_R).ifPresent(System.out::println);
    }
}
