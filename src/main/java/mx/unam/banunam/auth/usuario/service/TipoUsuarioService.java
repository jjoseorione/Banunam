package mx.unam.banunam.auth.usuario.service;

import mx.unam.banunam.auth.usuario.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.usuario.model.TipoUsuario;

public interface TipoUsuarioService {
    TipoUsuarioDTO convertirEnDTO(TipoUsuario tipoUsuario);
    TipoUsuario convertirEnEntidad(TipoUsuarioDTO dto);
}
