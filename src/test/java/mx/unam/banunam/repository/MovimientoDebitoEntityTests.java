package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.MovimientoDebito;
import mx.unam.banunam.system.model.OrigenDestinoMovimiento;
import mx.unam.banunam.system.model.TipoMovimiento;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.MovimientoDebitoRepository;
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
public class MovimientoDebitoEntityTests {
    @Autowired
    private MovimientoDebitoRepository movimientoDebitoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    private OrigenDestinoMovimientoRepository origenDestinoMovimientoRepository;

    private final Long FOLIO = 1L;
    private final Integer NO_CLIENTE = 3;
    private final Integer TIPO_MOVIMIENTO = 1;      //Corresponde a un depósito de nómina
    private final Integer ORIGEN_DESTINO = 1;      //Corresponde a una cuenta de débito empresarial

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
    @DisplayName(value = "Buscar todos los movimientos de débito")
    @Test
    void findAllMovimientosDebito() {
        System.out.println("Buscar todos los movimientos de débito");
        movimientoDebitoRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Buscar un movimiento de débito por folio")
    @Test
    void findMovimientoDebitoById() {
        System.out.println("Buscar movimiento de débito por Id");
        movimientoDebitoRepository.findById(FOLIO).ifPresent(System.out::println);
    }

    @Transactional
    @DisplayName(value = "Crear movimiento de débito")
    @Test
    void createMovimientoDebito() {
        System.out.println("Crear un movimiento de débito");
        Cliente cliente = clienteRepository.findById(NO_CLIENTE).orElse(null);
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(TIPO_MOVIMIENTO).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(ORIGEN_DESTINO).orElse(null);


        if (cliente != null && tipoMovimiento != null && origenDestinoMovimiento != null) {
            MovimientoDebito movimientoDebito = MovimientoDebito.builder()
                    .cuentaDebito(cliente.getCuentaDebito())
                    .tipoMov(tipoMovimiento)
                    .folio(null)
                    .monto(BigDecimal.ZERO)
                    .tipoOrigenDestino(origenDestinoMovimiento)
                    .concepto("Prueba de creación de movimiento")
                    .origenDestino("555555")
                    .timestampMov(LocalDateTime.now())
                    .build();
            movimientoDebitoRepository.save(movimientoDebito);
            System.out.println("Movimiento creado: \n" + movimientoDebito);
        }
    }

}
