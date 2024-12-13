package mx.unam.banunam.system.controller.customercarecenter;

import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.service.UsuarioService;
import mx.unam.banunam.auth.util.PropiedadesPerfiles;
import mx.unam.banunam.security.jwt.JWTTokenProviderUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/customer-care-center/")
@PreAuthorize("hasRole(${perfil.usuario.tipo2})")
public class CustomerCareCenterController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    @Qualifier("usuarioAuthenticationManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProviderUsuario jwtTokenProviderUsuario;
    @Autowired
    private PropiedadesPerfiles propiedadesPerfiles;

    private final String URI_BASE = "/customer-care-center/";

    @GetMapping("/")
    public String home(Model model) {

        log.info("Entra a customer-care-center raíz");
        return URI_BASE + "home";
    }
}
