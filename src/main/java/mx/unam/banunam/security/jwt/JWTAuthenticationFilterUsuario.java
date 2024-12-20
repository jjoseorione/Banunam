package mx.unam.banunam.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.security.dto.CredentialsDTO;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class JWTAuthenticationFilterUsuario extends OncePerRequestFilter {
    private final JWTTokenProviderUsuario tokenProvider;

    public JWTAuthenticationFilterUsuario(JWTTokenProviderUsuario tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        //log.info("JEEM: Security - Entra en JWTAuthenticationFilterUsuario.doFilterInternal");
        String jwt = "";
        if(request.getCookies() != null)
            for(Cookie cookie: request.getCookies())
                if(cookie.getName().equals("token"))
                    jwt = cookie.getValue();
        //log.info("JEEM: Security - El token es {} ", jwt);
        if(jwt == null || jwt.equals("")){
            //log.info("JEEM: Security - El token es nulo o vacío");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            if (tokenProvider.validateJwtToken(jwt)) {
                Claims body = tokenProvider.getClaims(jwt);
                var authorities = (List<Map<String, String>>) body.get("auth");
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());
                //String username = tokenProvider.getIssuer(jwt);
                String username = tokenProvider.getFullName(jwt);

                CredentialsDTO credentials = CredentialsDTO.builder()
                        .sub(tokenProvider.getSubject(jwt)).aud(tokenProvider.getAudience(jwt))
                        .exp(tokenProvider.getTokenExpiryFromJWT(jwt).getTime())
                        .iat(tokenProvider.getTokenIatFromJWT(jwt).getTime())
                        .build();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, credentials, simpleGrantedAuthorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            log.error("Can NOT set user authentication -> Message: {}", exception.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
