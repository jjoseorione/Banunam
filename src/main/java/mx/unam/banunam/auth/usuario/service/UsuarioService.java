package mx.unam.banunam.auth.usuario.service;

import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import mx.unam.banunam.auth.usuario.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO convertitEnDTO(Usuario entidad);
    Usuario convertirEnEntidad(UsuarioDTO dto);
    UsuarioDTO buscarUsuarioPorId(Integer id);
    UsuarioDTO salvar(UsuarioDTO usuario, Boolean update);
    UsuarioDTO buscarUsuarioPorUsuario(String usuario, Boolean login);
    List<UsuarioDTO> listarUsuariosPorTipoUsuarioAlias(String alias);
    void eliminarUsuarioPorId(Integer id);
}
