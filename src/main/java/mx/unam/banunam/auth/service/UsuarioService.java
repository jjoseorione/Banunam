package mx.unam.banunam.auth.service;

import mx.unam.banunam.auth.dto.UsuarioDTO;
import mx.unam.banunam.auth.model.Usuario;

public interface UsuarioService {
    //List<Usuario> listarUsuarios();
    UsuarioDTO convertitEnDTO(Usuario entidad);
    Usuario convertirEnEntidad(UsuarioDTO dto);
    UsuarioDTO buscarUsuarioPorId(Integer id);
    UsuarioDTO salvar(UsuarioDTO usuario);
    UsuarioDTO buscarUsuarioPorUsuario(String correo);
}
