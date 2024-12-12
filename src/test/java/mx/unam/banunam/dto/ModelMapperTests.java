package mx.unam.banunam.dto;

import mx.unam.banunam.auth.cliente.dto.ClienteAuthDTO;
import mx.unam.banunam.auth.cliente.service.ClienteAuthService;
import mx.unam.banunam.auth.usuario.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import mx.unam.banunam.auth.usuario.model.TipoUsuario;
import mx.unam.banunam.auth.usuario.model.Usuario;
import mx.unam.banunam.auth.usuario.repository.TipoUsuarioRepository;
import mx.unam.banunam.auth.usuario.repository.UsuarioRepository;
import mx.unam.banunam.auth.usuario.service.TipoUsuarioService;
import mx.unam.banunam.auth.usuario.service.UsuarioService;
import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.TarjetaDebito;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.TarjetaDebitoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

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
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ClienteAuthService clienteAuthService;
    @Autowired
    TarjetaDebitoRepository tarjetaDebitoRepository;

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
        final Integer TIPO_USUARIO = 1;
        TipoUsuario tipoUsuarioBD = tipoUsuarioRepository.findById(TIPO_USUARIO).orElse(null);
        Assertions.assertNotNull(tipoUsuarioBD);
        TipoUsuarioDTO tipoUsuarioDTO = tipoUsuarioService.convertirEnDTO(tipoUsuarioBD);

        TipoUsuario tipoUsuario = tipoUsuarioService.convertirEnEntidad(tipoUsuarioDTO);
        Assertions.assertEquals(tipoUsuarioDTO.getTipoUsuario(), tipoUsuario.getTipoUsuario());
        Assertions.assertEquals(tipoUsuarioDTO.getAlias(), tipoUsuario.getAlias());
        Assertions.assertEquals(tipoUsuarioDTO.getDescripcion(), tipoUsuario.getDescripcion());

        System.out.println(tipoUsuario);
        System.out.println(tipoUsuarioDTO);
    }

    @DisplayName(value = "Cliente a ClienteAuthDTO")
    @Test
    @Transactional
    void convertClienteToClienteAuthDTO(){
        System.out.println("Conversión de Cliente a ClienteAuthDTO");
        final Integer CLIENTE = 3;
        Cliente clienteBD = clienteRepository.findById(CLIENTE).orElse(null);
        Assertions.assertNotNull(clienteBD);
        Assertions.assertNotNull(clienteBD.getCuentaDebito());

        ClienteAuthDTO clienteAuthDTO = clienteAuthService.convertirEnDTO(clienteBD);
        //Se prueba que las tarjetas no vengan vacías al realizar la conversión
        Assertions.assertFalse(clienteAuthDTO.getTarjetasDebito().isEmpty());
        //Se revisa que las tarjetas del DTO existan y pertenezcan al cliente
        clienteAuthDTO.getTarjetasDebito().forEach((tdd) -> {
            TarjetaDebito tarjetaDebito = tarjetaDebitoRepository.findById(tdd).orElse(null);
            Assertions.assertNotNull(tarjetaDebito);
            Assertions.assertEquals(clienteBD.getNoCliente(), tarjetaDebito.getCuentaDebito().getCliente().getNoCliente());
            System.out.println(tarjetaDebito);
            System.out.println(tdd);
        });
        System.out.println(clienteBD);
        System.out.println(clienteAuthDTO);
    }

    @DisplayName(value = "ClienteAuthDTO a Cliente")
    @Test
    @Transactional
    void convertClienteAuthDTOToCliente(){
        System.out.println("Conversión de ClienteAuthDTO a Cliente");
        final Integer CLIENTE = 3;
        Cliente clienteBD = clienteRepository.findById(CLIENTE).orElse(null);
        Assertions.assertNotNull(clienteBD);
        Assertions.assertNotNull(clienteBD.getCuentaDebito());

        ClienteAuthDTO clienteAuthDTO = clienteAuthService.convertirEnDTO(clienteBD);

        Cliente cliente = clienteAuthService.convertirEnEntidad(clienteAuthDTO);
        Assertions.assertEquals(clienteBD, cliente);
        System.out.println("Origen:  " + clienteAuthDTO);
        System.out.println("Resultado" + cliente);


    }
}
