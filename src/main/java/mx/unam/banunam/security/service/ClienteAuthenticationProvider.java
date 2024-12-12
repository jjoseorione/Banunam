package mx.unam.banunam.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.util.PropiedadesPerfiles;
import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@AllArgsConstructor
public class ClienteAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private PropiedadesPerfiles propiedadesPerfiles;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteAuthenticationProvider(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        final String AUTHORITY = propiedadesPerfiles.getClienteTipo1();
        log.info("JEEM: Security - ClienteAuthenticationProvider.authenticate");
        String noTDD = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Cliente cliente = clienteRepository.findByNoTarjetaDebito(noTDD)
                .orElseThrow(() -> new BadCredentialsException("Número de TDD o contraseña incorrectos"));
        //Filtro que verifica que el usuario no esté bloqueado ni expirado
        //if(usuario.getEstatus() != 'A' || usuario.getFechaExpUsuario().isBefore(LocalDate.now()))
            //throw new DisabledException("Usuario bloqueado o expirado");
        if (passwordEncoder.matches(pwd, cliente.getContrasena())) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(AUTHORITY));
            return new UsernamePasswordAuthenticationToken(noTDD, pwd, authorities);
        } else {
//            usuario.setIntentos(usuario.getIntentos() + 1);
//            if(usuario.getIntentos() == 3)
//                usuario.setEstatus('B');
//            usuarioRepository.save(usuario);
//            log.info("########## JEEM: Contraseña incorrecta Usuario: {} Intentos: {}", usuario.getUsuario(), usuario.getIntentos());
            throw new BadCredentialsException("Número de TDD o contraseña incorrectos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
