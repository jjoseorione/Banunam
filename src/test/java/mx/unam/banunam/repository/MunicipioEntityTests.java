package mx.unam.banunam.repository;

import mx.unam.banunam.system.model.Municipio;
import mx.unam.banunam.system.repository.MunicipioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class MunicipioEntityTests {
    @Autowired
    MunicipioRepository municipioRepository;

    private final Integer MUNICIPIO = 1;

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

    @DisplayName(value = "Mostrar todos los municipios")
    @Test
    void findAllMunicipios(){
        System.out.println("Buscar todos los municipios en la BBDD");
        municipioRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Buscar municipio por id")
    @Test
    void findMunicipioById(){
        System.out.println("Buscar municipio por id: " + MUNICIPIO);
        Optional<Municipio> municipio = municipioRepository.findById(MUNICIPIO);
        municipio.ifPresent(System.out::println);
        Assertions.assertEquals("Aguascalientes", municipio.get().getNombre());
    }
}
