package mx.unam.banunam.repository;

import mx.unam.banunam.auth.model.TipoUsuario;
import mx.unam.banunam.auth.model.Usuario;
import mx.unam.banunam.auth.repository.TipoUsuarioRepository;
import mx.unam.banunam.auth.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class UsuarioEntityTests {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    private final Integer ID_USUARIO = 1;
    private final String CORREO = "juancarlos.medina@banunam.com";

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

    @DisplayName(value = "Buscar Usuario por id")
    @Test
    @Transactional
    void findUsuarioById(){
        System.out.println("Buscar customer-care-center por id");
        Usuario usuario = usuarioRepository.findById(ID_USUARIO).orElse(null);
        Assertions.assertNotNull(usuario);
        System.out.println("El customer-care-center es: " + usuario);
    }

    @DisplayName(value = "Buscar todos los usuarios")
    @Test
    @Transactional
    void findAllUsuarios(){
        System.out.println("Listar todos los usuarios");
        usuarioRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Crear customer-care-center")
    @Test
    @Transactional
    void createUsuario(){
        System.out.println("Crear customer-care-center");
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(1).orElse(null);
        if(tipoUsuario != null) {
            Usuario usuario = Usuario.builder()
                    .idUsuario(null)
                    .usuario("PRUEB01")
                    .nombre("Juan")
                    .apellido1("Sánchez")
                    .apellido2("Escobar")
                    .correo("juan.sanchez@gmail.com")
                    .contrasena("t3mp0r4l")
                    .intentos(null)
                    .estatus('A')
                    .tipoUsuario(tipoUsuario)
                    .fechaExpUsuario(null)
                    .fechaExpContrasena(null)
                    .build();
            usuarioRepository.save(usuario);
            System.out.println("Usuario creado: \n" + usuario);
        }
    }

    @DisplayName(value = "Existe customer-care-center por correo")
    @Test
    @Transactional
    void existsUsuarioByCorreo(){
        System.out.println("Existencia de customer-care-center por correo " + CORREO);
        Boolean existe = usuarioRepository.existsByCorreo(CORREO);
        Assertions.assertTrue(existe);
        System.out.println(existe);
    }

    @DisplayName(value = "Eliminar customer-care-center por Id")
    @Test
    @Transactional
    void deleteUsuarioById(){
        System.out.println("Eliminar customer-care-center por id");
        usuarioRepository.deleteById(1);
        System.out.println("El customer-care-center fue eliminado");
    }


}
