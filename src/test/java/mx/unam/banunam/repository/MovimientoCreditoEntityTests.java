package mx.unam.banunam.repository;


import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.MovimientoCredito;
import mx.unam.banunam.system.model.OrigenDestinoMovimiento;
import mx.unam.banunam.system.model.TipoMovimiento;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.MovimientoCreditoRepository;
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
public class MovimientoCreditoEntityTests {
    @Autowired
    private MovimientoCreditoRepository movimientoCreditoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    private OrigenDestinoMovimientoRepository origenDestinoMovimientoRepository;

    private final Long FOLIO = 1L;
    private final Integer NO_CLIENTE = 3;
    private final Integer TIPO_MOVIMIENTO = 7;
    private final Integer ORIGEN_DESTINO = 2;

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
    @DisplayName(value = "Buscar todos los movimientos de crédito")
    @Test
    void findAllMovimientosCredito(){
        System.out.println("Buscar todos los movimientos de crédito");
        movimientoCreditoRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Buscar un movimiento de crédito por folio")
    @Test
    void findMovimientoCreditoById(){
        System.out.println("Buscar movimiento de credito por Id");
        movimientoCreditoRepository.findById(FOLIO).ifPresent(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Crear movimiento de débito")
    @Test
    void createMovimientoCredito(){
        System.out.println("Crear movimiento de crédito");
        Cliente cliente = clienteRepository.findById(NO_CLIENTE).orElse(null);
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(TIPO_MOVIMIENTO).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(ORIGEN_DESTINO).orElse(null);


        if (cliente != null && tipoMovimiento != null && origenDestinoMovimiento != null) {
            MovimientoCredito movimientoCredito = MovimientoCredito.builder()
                    .folio(null)
                    .timestampMov(LocalDateTime.now())
                    .monto(BigDecimal.ZERO)
                    .tipoMov(tipoMovimiento)
                    .cuentaCredito(cliente.getCuentaCredito())
                    .origenDestino("10000001")
                    .tipo_origen_destino(origenDestinoMovimiento)
                    .concepto("Prueba de movimiento de crédito")
                    .build();
            movimientoCreditoRepository.save(movimientoCredito);
            System.out.println("Movimiento creado: \n" + movimientoCredito);
        }
    }
}
