package mx.unam.banunam.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTTokenProviderUsuario {
    private String secret;
    private int jwtExpirationInMs;
    private SecretKey key;

    @Value("${jwt.secretUser}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.expirationDateInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateJwtToken(Authentication authentication, UsuarioDTO user) {
        Claims claims = Jwts.claims().setSubject("UNAM").setIssuer(user.getUsuario())
                .setAudience("JAVA");
        claims.put("principal", authentication.getPrincipal());
        claims.put("auth", authentication.getAuthorities().stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
                .collect(Collectors.toList()));
        claims.put("issid", user.getIdUsuario());
        claims.put("issname", user.getNombre() + " " + user.getApellido1());
        key = Keys.hmacShaKeyFor(secret.getBytes());
        long exp = System.currentTimeMillis() + jwtExpirationInMs;

        log.info("JEEM - Security. Se genera el token en JWTTokenProviderUsuario.generateJwtToken");
        log.info("JEEM - Security. Expiración de token: {}", new Date(exp));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(exp))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaims(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getFullName(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        var body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return (String) body.get("issname");
    }

    public String getSubject(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getIssuer(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getIssuer();
    }

    public String getAudience(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getAudience();
    }

    public Date getTokenExpiryFromJWT(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    public Date getTokenIatFromJWT(String token) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getIssuedAt();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException exception) {
            log.error("Invalid JWT token -> Message: {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            log.error("Expired JWT token -> Message: {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Unsupported JWT token -> Message: {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("JWT claims string is empty -> Message: {}", exception.getMessage());
        }
        return false;
    }

    public long getExpiryDuration() {
        return jwtExpirationInMs;
    }
}