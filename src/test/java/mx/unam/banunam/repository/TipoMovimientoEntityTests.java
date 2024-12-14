package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.TipoMovimiento;
import mx.unam.banunam.system.repository.TipoMovimientoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class TipoMovimientoEntityTests {
    @Autowired
    TipoMovimientoRepository tipoMovimientoRepository;

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

    @DisplayName(value = "Buscar todos los tipos de movimiento")
    @Test
    @Transactional
    void findAllTiposMovimiento(){
        System.out.println("Mostrar todos los tipos de movimiento");
        tipoMovimientoRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Crear tipos de movimiento")
    @Test
    @Transactional
    void createTipoMovimiento(){
        System.out.println("Crear tipos de movimiento");
        TipoMovimiento t1 =
            TipoMovimiento.builder().tipoMov(null).descripcion("Prueba tipos_movimiento C").signoDebito('+').tipoCuenta('C').build();
        TipoMovimiento t2 =
                TipoMovimiento.builder().tipoMov(null).descripcion("Prueba tipos_movimiento D").signoDebito('+').tipoCuenta('D').build();
        TipoMovimiento t3 =
                TipoMovimiento.builder().tipoMov(null).descripcion("Prueba tipos_movimiento P").signoDebito('+').tipoCuenta('P').build();
        tipoMovimientoRepository.saveAll(Arrays.asList(t1, t2, t3));
        System.out.println("Tipos de Movimiento creados: ");
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
    }
}
