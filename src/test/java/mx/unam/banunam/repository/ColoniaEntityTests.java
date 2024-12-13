package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Colonia;
import mx.unam.banunam.system.repository.ColoniaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class ColoniaEntityTests {
    @Autowired
    private ColoniaRepository coloniaRepository;

    private final Integer COLONIA = 1;

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

    @DisplayName(value = "Mostrar todos las colonias")
    @Test
    void findAllColonias(){
        System.out.println("Mostrar todos las colonias");
        coloniaRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Buscar colonia por id")
    @Test
    void findColoniaById(){
        System.out.println("Buscar colonia por id: " + COLONIA);
        Optional<Colonia> colonia = coloniaRepository.findById(COLONIA);
        colonia.ifPresent(System.out::println);
        Assertions.assertEquals("Aguascalientes Centro", colonia.get().getNombre());
    }

    @DisplayName(value = "Buscar colonias por cp")
    @Test
    void findColoniasByCp(){
        final String CP_1 = "20000";
        final String CP_2 = "20010";
        System.out.println("Buscar colonias por cp: " + CP_1);
        List<Colonia> colonias = coloniaRepository.findByCp(CP_1);
        Assertions.assertFalse(colonias.isEmpty());
        System.out.println("Colonias: " + colonias);

        System.out.println("Buscar colonias por cp: " + CP_2);
        colonias = coloniaRepository.findByCp(CP_2);
        Assertions.assertFalse(colonias.isEmpty());
        System.out.println("Colonias: " + colonias);
    }
}
