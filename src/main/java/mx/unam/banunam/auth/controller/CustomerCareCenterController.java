package mx.unam.banunam.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.dto.UsuarioDTO;
import mx.unam.banunam.auth.exception.UsuarioNotFoundException;
import mx.unam.banunam.auth.service.UsuarioService;
import mx.unam.banunam.auth.util.PropiedadesPerfiles;
import mx.unam.banunam.security.jwt.JWTTokenProvider;
import mx.unam.banunam.security.request.JwtRequest;
import mx.unam.banunam.security.request.LoginUserRequest;
import mx.unam.banunam.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequestMapping(path = "/customer-care-center/")
//@PreAuthorize("hasRole('EXEC')")
@PreAuthorize("hasRole(${usuario.tipo2})")
public class CustomerCareCenterController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private PropiedadesPerfiles propiedadesPerfiles;


	@GetMapping("/")
	public String home(Model model) {

		log.info("Entra a raíz");
		model.addAttribute("text", "Hola");
		return "/customer-care-center/home";
	}

	@GetMapping("/login")
	public String login() {
		return "/customer-care-center/login";
	}

	@PostMapping("/login_success_handler")
	public String loginSuccessHandler() {
		log.info("Logging user login success...");
		return "/customer-care-center/home";
	}

	@PostMapping("/login_failure_handler")
	public String loginFailureHandler() {
		log.info("Login failure handler....");
		return "/customer-care-center/login";
	}

	@GetMapping("/prueba1")
	public String prueba(){
		return "prueba1";
	}


	@PostMapping("/token")
	public String createAuthenticationToken(Model model, HttpSession session,
											@ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse res, RedirectAttributes flash) throws Exception {

		final String TIPO_USUARIO = propiedadesPerfiles.getTipo2();
		log.info("LoginUserRequest {}", loginUserRequest);
		log.info("########## Perfil requerido: {}", propiedadesPerfiles.getTipo2());		//Perfil tipo2: EXEC
		try {
			UsuarioDTO user = usuarioService.buscarUsuarioPorUsuario(loginUserRequest.getUsername());
			//Filtro que verifica que el usuario sea del tipo requerido
			log.info("########## JEEM: Filtro: estatus={} tipoUsuario={} fechaExpUsuario={}",
					user.getEstatus(), user.getTipoUsuario(), user.getFechaExpUsuario());
			if(user.getTipoUsuario().equals(TIPO_USUARIO)) {
				Authentication authentication = authenticate(loginUserRequest.getUsername(),
						loginUserRequest.getPassword(), TIPO_USUARIO);
				log.info("authentication {}", authentication);
				String jwtToken = jwtTokenProvider.generateJwtToken(authentication, user);
				log.info("jwtToken {}", jwtToken);
				JwtRequest jwtRequest = new JwtRequest(jwtToken, user.getIdUsuario(), user.getUsuario(),
						jwtTokenProvider.getExpiryDuration(), authentication.getAuthorities());
				log.info("jwtRequest {}", jwtRequest);
				Cookie cookie = new Cookie("token", jwtToken);
				cookie.setMaxAge(Integer.MAX_VALUE);
				res.addCookie(cookie);
				session.setAttribute("msg", "Login OK!");
			}
			else{
				log.info("########## JEEM: El usuario no es del perfil requerido");
				flash.addFlashAttribute("loginError", true);
				return "redirect:/customer-care-center/login";
			}
		} catch (UsuarioNotFoundException | BadCredentialsException | DisabledException e) {
			flash.addFlashAttribute("loginError", true);
			return "redirect:/customer-care-center/login";
		}
		return "redirect:/customer-care-center/";
	}

	private Authentication authenticate(String username, String password, String tipoUsuario) throws Exception {
		log.info("########## JEEM: Se accede a CustomerCareCenterController.authenticate");
		log.info("########## JEEM: Authentication Manager: {}", authenticationManager);
		try {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(tipoUsuario));
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException | BadCredentialsException e) {
			log.info("########## JEEM: {} {}", e.getClass(), e.getMessage());
			throw e;
		}
	}
}
