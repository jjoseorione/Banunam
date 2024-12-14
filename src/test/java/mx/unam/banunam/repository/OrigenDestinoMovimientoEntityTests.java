package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.OrigenDestinoMovimiento;
import mx.unam.banunam.system.repository.OrigenDestinoMovimientoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar tests básicos de la entidad CuentaDebito, así como de las consultas
 * heredadas de CrudRepository a ClienteRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class OrigenDestinoMovimientoEntityTests {
    @Autowired
    private OrigenDestinoMovimientoRepository repository;

    private final Integer ID_ORIGEN_DESTINO = 1;

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

    @DisplayName("Obtener todos los tipos de origen/destino")
    @Test
    void findAllOrigenDestinoMovimiento(){
        System.out.println("Obtener todos los tipos de origen o destino");
        repository.findAll().forEach(System.out::println);
    }

    @DisplayName("Crear un nuevo tipo de origen/destino")
    @Test
    void createOrigenDestinoMovimiento(){
        final String descr = "Prueba";
        System.out.println("Crear un nuevo tipo de origen/destino");
        OrigenDestinoMovimiento origenDestino = OrigenDestinoMovimiento.builder()
                .tipoOrigenDestino(null)
                .descripcion(descr)
                .build();
        repository.save(origenDestino);
        System.out.println(origenDestino);
    }

    @DisplayName("Buscar tipo de origen/destino por Id")
    @Test
    void findOrigenDestinoMovimientoById(){
        System.out.println("Buscar tipo de origen/destino por id " + ID_ORIGEN_DESTINO);
        repository.findById(ID_ORIGEN_DESTINO).ifPresent(System.out::println);
    }
}
