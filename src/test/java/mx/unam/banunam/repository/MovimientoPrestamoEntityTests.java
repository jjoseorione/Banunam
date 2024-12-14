package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.MovimientoPrestamo;
import mx.unam.banunam.system.model.OrigenDestinoMovimiento;
import mx.unam.banunam.system.model.TipoMovimiento;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.MovimientoPrestamoRepository;
import mx.unam.banunam.system.repository.OrigenDestinoMovimientoRepository;
import mx.unam.banunam.system.repository.TipoMovimientoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class MovimientoPrestamoEntityTests {
    @Autowired
    private MovimientoPrestamoRepository movimientoPrestamoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    private OrigenDestinoMovimientoRepository origenDestinoMovimientoRepository;

    private final Long FOLIO = 1L;
    private final Integer NO_CLIENTE = 3;
    private final Integer TIPO_MOVIMIENTO = 11;      //Corresponde a un depósito de préstamo
    private final Integer ORIGEN_DESTINO = 2;      //Corresponde a una cuenta de débito personal

    @BeforeEach
    void espaciado() {
        System.out.println();
        System.out.println("••••••••••••••••••••••••••••••");
    }

    @AfterEach
    void espaciadof() {
        System.out.println("••••••••••••••••••••••••••••••");
        System.out.println();
    }

    @Transactional
    @DisplayName(value = "Buscar todos los movimientos de préstamo")
    @Test
    void findAllMovimientosPrestamo() {
        System.out.println("Buscar todos los movimientos de préstamo");
        movimientoPrestamoRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Buscar un movimiento de préstamo por folio")
    @Test
    void findMovimientoPrestamoById() {
        System.out.println("Buscar movimiento de préstamo por Id");
        movimientoPrestamoRepository.findById(FOLIO).ifPresent(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Crear movimiento de préstamo")
    @Test
    void createMovimientoPrestamo() {
        System.out.println("Crear un movimiento de préstamo");
        Cliente cliente = clienteRepository.findById(NO_CLIENTE).orElse(null);
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(TIPO_MOVIMIENTO).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(ORIGEN_DESTINO).orElse(null);


        if (cliente != null && tipoMovimiento != null && origenDestinoMovimiento != null) {
            MovimientoPrestamo movimientoPrestamo = MovimientoPrestamo.builder()
                    .folio(null)
                    .timestampMov(LocalDateTime.now())
                    .monto(BigDecimal.ZERO)
                    .tipoMov(tipoMovimiento)
                    .cuentaPrestamo(cliente.getCuentaPrestamo())
                    .origenDestino("555555")
                    .tipoOrigenDestino(origenDestinoMovimiento)
                    .concepto("Prueba de creación de movimiento")
                    .build();
            movimientoPrestamoRepository.save(movimientoPrestamo);
            System.out.println("Movimiento creado: \n" + movimientoPrestamo);
        }
    }
}
