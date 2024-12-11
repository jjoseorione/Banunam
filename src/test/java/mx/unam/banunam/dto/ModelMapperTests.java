package mx.unam.banunam.dto;

import mx.unam.banunam.auth.usuario.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import mx.unam.banunam.auth.usuario.model.TipoUsuario;
import mx.unam.banunam.auth.usuario.model.Usuario;
import mx.unam.banunam.auth.usuario.repository.TipoUsuarioRepository;
import mx.unam.banunam.auth.usuario.repository.UsuarioRepository;
import mx.unam.banunam.auth.usuario.service.TipoUsuarioService;
import mx.unam.banunam.auth.usuario.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class ModelMapperTests {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TipoUsuarioService tipoUsuarioService;
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

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

    @DisplayName(value = "Usuario a UsuarioDTO")
    @Test
    void convertUsuarioToUsuarioDTO(){
        System.out.println("Conversión de Usuario a UsuarioDTO");
        Usuario usuario = usuarioRepository.findById(1).orElse(null);
        UsuarioDTO usuarioDTO = usuarioService.convertitEnDTO(usuario);
        Assertions.assertEquals("ADMIN", usuarioDTO.getTipoUsuario());
        System.out.println(usuario);
        System.out.println(usuarioDTO);
    }

    @DisplayName(value = "UsuarioDTO a Usuario")
    @Test
    void convertUsuarioDTOToUsuario(){
        System.out.println("Conversión de UsuarioDTO a Usuario");
        Usuario usuarioBD = usuarioRepository.findById(1).orElse(null);
        UsuarioDTO usuarioDTO = usuarioService.convertitEnDTO(usuarioBD);

        Usuario usuario = usuarioService.convertirEnEntidad(usuarioDTO);
        Assertions.assertNotNull(usuario.getTipoUsuario());

        System.out.println(usuario);
        System.out.println(usuarioDTO);
    }

    @DisplayName(value = "TipoUsuario a TipoUsuarioDTO")
    @Test
    void convertTipoUsuarioToTipoUsuarioDTO(){
        System.out.println("Conversión de TipoUsuario a TipoUsuarioDTO");
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(1).orElse(null);
        Assertions.assertNotNull(tipoUsuario);

        TipoUsuarioDTO tipoUsuarioDTO = tipoUsuarioService.convertirEnDTO(tipoUsuario);
        Assertions.assertEquals(tipoUsuario.getTipoUsuario(), tipoUsuarioDTO.getTipoUsuario());
        Assertions.assertEquals(tipoUsuario.getAlias(), tipoUsuarioDTO.getAlias());
        Assertions.assertEquals(tipoUsuario.getDescripcion(), tipoUsuarioDTO.getDescripcion());

        System.out.println(tipoUsuario);
        System.out.println(tipoUsuarioDTO);
    }

    @DisplayName(value = "TipoUsuarioDTO a TipoUsuario")
    @Test
    void convertTipoUsuarioDTOToTipoUsuario(){
        System.out.println("Conversión de TipoUsuarioDTO a TipoUsuario");
        TipoUsuario tipoUsuarioBD = tipoUsuarioRepository.findById(1).orElse(null);
        Assertions.assertNotNull(tipoUsuarioBD);
        TipoUsuarioDTO tipoUsuarioDTO = tipoUsuarioService.convertirEnDTO(tipoUsuarioBD);

        TipoUsuario tipoUsuario = tipoUsuarioService.convertirEnEntidad(tipoUsuarioDTO);
        Assertions.assertEquals(tipoUsuarioDTO.getTipoUsuario(), tipoUsuario.getTipoUsuario());
        Assertions.assertEquals(tipoUsuarioDTO.getAlias(), tipoUsuario.getAlias());
        Assertions.assertEquals(tipoUsuarioDTO.getDescripcion(), tipoUsuario.getDescripcion());

        System.out.println(tipoUsuario);
        System.out.println(tipoUsuarioDTO);
    }
}
