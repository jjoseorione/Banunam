package mx.unam.banunam.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.model.Usuario;
import mx.unam.banunam.auth.usuario.repository.UsuarioRepository;
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
@AllArgsConstructor
public class UsuarioAuthenticationProvider implements AuthenticationProvider {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        log.info("JEEM: Security - UsuarioAuthenticationProvider.authenticate");
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));
        //Filtro que verifica que el usuario no esté bloqueado ni expirado
        if(usuario.getEstatus() != 'A' || usuario.getFechaExpUsuario().isBefore(LocalDate.now()))
            throw new DisabledException("Usuario bloqueado o expirado");
        if (passwordEncoder.matches(pwd, usuario.getContrasena())) {
            usuario.setIntentos(0);
            usuarioRepository.save(usuario);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(usuario.getTipoUsuario().getAlias()));
            return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
        } else {
            usuario.setIntentos(usuario.getIntentos() + 1);
            if(usuario.getIntentos() == 3)
                usuario.setEstatus('B');
            usuarioRepository.save(usuario);
            log.info("########## JEEM: Contraseña incorrecta Usuario: {} Intentos: {}", usuario.getUsuario(), usuario.getIntentos());
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
