package mx.unam.banunam.auth.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class PropiedadesPerfiles {

    @Value("${perfil.usuario.tipo1}")
    private String usuarioTipo1;

    @Value("${perfil.usuario.tipo2}")
    private String usuarioTipo2;

    @Value("${perfil.cliente.tipo1}")
    private String clienteTipo1;
}