package mx.unam.banunam.auth.usuario.service;

import mx.unam.banunam.auth.usuario.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.usuario.model.TipoUsuario;

import java.util.List;

public interface TipoUsuarioService {
    List<TipoUsuarioDTO> listarTiposUsuario();
    TipoUsuarioDTO convertirEnDTO(TipoUsuario tipoUsuario);
    TipoUsuario convertirEnEntidad(TipoUsuarioDTO dto);
}
