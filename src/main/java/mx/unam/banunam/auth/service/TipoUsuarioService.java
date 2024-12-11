package mx.unam.banunam.auth.service;

import mx.unam.banunam.auth.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.model.TipoUsuario;
import org.springframework.stereotype.Service;

public interface TipoUsuarioService {
    TipoUsuarioDTO convertirEnDTO(TipoUsuario tipoUsuario);
    TipoUsuario convertirEnEntidad(TipoUsuarioDTO dto);
}
