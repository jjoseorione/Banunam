package mx.unam.banunam.auth.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class PropiedadesPerfiles {

    @Value("${usuario.tipo1}")
    private String tipo1;

    @Value("${usuario.tipo2}")
    private String tipo2;
}