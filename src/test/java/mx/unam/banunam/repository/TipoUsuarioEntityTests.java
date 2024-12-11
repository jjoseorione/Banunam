package mx.unam.banunam.repository;

import mx.unam.banunam.auth.usuario.model.TipoUsuario;
import mx.unam.banunam.auth.usuario.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class TipoUsuarioEntityTests {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    private final Integer ID = 1;

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

    @DisplayName(value = "Listar todos los tipos de customer-care-center")
    @Test
    void findAllTiposUsuario(){
        tipoUsuarioRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Buscar tipoUsuario por id")
    @Test
    void findTipoUsuarioById(){
        System.out.println("Buscar tipoUsuario por id");
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(ID).orElse(null);
        Assertions.assertNotNull(tipoUsuario);
        System.out.println(tipoUsuario);
    }

    @DisplayName(value = "Crear tipoUsuario")
    @Test
    void createTipoUsuario(){
        System.out.println("Crear tipoUsuario");
        TipoUsuario tipoUsuario = TipoUsuario.builder()
                .tipoUsuario(null)
                .alias("PRUEBA")
                .descripcion("Prueba de creación de usuario")
                .build();
        tipoUsuarioRepository.save(tipoUsuario);
        System.out.println(tipoUsuario);
    }

}
