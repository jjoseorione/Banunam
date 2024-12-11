package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Estado;
import mx.unam.banunam.system.repository.EstadoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class EstadoEntityTests {
    @Autowired
    private EstadoRepository estadoRepository;

    private final Integer ESTADO = 1;

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

    @DisplayName(value = "Mostrar todos los estados")
    @Test
    void findAllEstados(){
        System.out.println("Mostrar todos los estados");
        estadoRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Buscar estado por id")
    @Test
    void findEstadoById(){
        System.out.println("Buscar estado por id: " + ESTADO);
        Optional<Estado> estado = estadoRepository.findById(ESTADO);
        estado.ifPresent(System.out::println);
        Assertions.assertEquals("Aguascalientes", estado.get().getNombre());
    }
}
