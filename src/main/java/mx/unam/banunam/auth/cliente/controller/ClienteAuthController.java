package mx.unam.banunam.auth.cliente.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.cliente.dto.ClienteAuthDTO;
import mx.unam.banunam.auth.cliente.service.ClienteAuthService;
import mx.unam.banunam.auth.exception.ClienteNotFoundException;
import mx.unam.banunam.security.jwt.JWTTokenProviderCliente;
import mx.unam.banunam.security.request.JwtRequest;
import mx.unam.banunam.security.request.LoginUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping(path = "/banca-en-linea/")
@PreAuthorize("hasRole(${perfil.usuario.tipo1})")
public class ClienteAuthController {
    @Autowired
    private ClienteAuthService clienteAuthService;
    @Autowired
    @Qualifier("clienteAuthenticationManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProviderCliente jwtTokenProviderCliente;



    @GetMapping("/")
    public String home(Model model){
        log.info("########## JEEM: Entra a banca-en-linea raíz");
        model.addAttribute("text", "BeL");
        return "/banca-en-linea/home";
    }

    @GetMapping("/login")
    public String login() {
        return "/banca-en-linea/login";
    }

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        log.info("Logging user login success...");
        return "/banca-en-linea/home";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        log.info("Login failure handler....");
        return "/banca-en-linea/login";
    }

    @GetMapping("/onlyAuthorized")
    public String onlyAuthorized(){
        return "/banca-en-linea/onlyAuth";
    }

    @PostMapping("/token")
    public String createAuthenticationToken(Model model, HttpSession session,
                                            @ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse res, RedirectAttributes flash) throws Exception {

        //final String TIPO_USUARIO = propiedadesPerfiles.getTipo2();
        log.info("LoginUserRequest {}", loginUserRequest);
        //log.info("########## Perfil requerido: {}", propiedadesPerfiles.getTipo2());		//Perfil tipo2: EXEC
        try {
            ClienteAuthDTO cliente = clienteAuthService.buscarClientePorNoTDD(loginUserRequest.getUsername());

                Authentication authentication = authenticate(loginUserRequest.getUsername(),
                        loginUserRequest.getPassword());
                log.info("authentication {}", authentication);
                String jwtToken = jwtTokenProviderCliente.generateJwtToken(authentication, cliente);
                log.info("jwtToken {}", jwtToken);
                JwtRequest jwtRequest = new JwtRequest(jwtToken, cliente.getNoCliente(), cliente.getCorreo(),
                        jwtTokenProviderCliente.getExpiryDuration(), authentication.getAuthorities());
                log.info("jwtRequest {}", jwtRequest);
                Cookie cookie = new Cookie("tokenCliente", jwtToken);
                cookie.setMaxAge(Integer.MAX_VALUE);
                res.addCookie(cookie);
                session.setAttribute("msg", "Login OK!");

        } catch (ClienteNotFoundException | BadCredentialsException | DisabledException e) {
            flash.addFlashAttribute("loginError", true);
            return "redirect:/banca-en-linea/login";
        }
        return "redirect:/banca-en-linea/";
    }

    private Authentication authenticate(String noTDD, String password) throws Exception {
        log.info("########## JEEM: Se accede a CustomerCareCenterController.authenticate");
        log.info("########## JEEM: Authentication Manager: {}", authenticationManager);
        try {
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(authority));
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(noTDD, password));
        } catch (DisabledException | BadCredentialsException e) {
            log.info("########## JEEM: {} {}", e.getClass(), e.getMessage());
            throw e;
        }
    }


}
