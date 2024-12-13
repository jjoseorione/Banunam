package mx.unam.banunam.auth.usuario.service.impl;

import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import mx.unam.banunam.auth.exception.UsuarioAlreadyExistsException;
import mx.unam.banunam.auth.exception.UsuarioNotFoundException;
import mx.unam.banunam.auth.usuario.model.TipoUsuario;
import mx.unam.banunam.auth.usuario.model.Usuario;
import mx.unam.banunam.auth.usuario.repository.TipoUsuarioRepository;
import mx.unam.banunam.auth.usuario.repository.UsuarioRepository;
import mx.unam.banunam.auth.usuario.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    ModelMapper modelMapper;
    @Qualifier("passwordEncoderUser")
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::convertitEnDTO).toList();
    }

    @Override
    public UsuarioDTO convertitEnDTO(Usuario entidad) {
        return modelMapper.map(entidad, UsuarioDTO.class);
    }

    @Override
    public Usuario convertirEnEntidad(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        if(usuario.getTipoUsuario() == null){
            TipoUsuario tipoUsuario = tipoUsuarioRepository.findByAlias(dto.getTipoUsuario()).orElse(null);
            usuario.setTipoUsuario(tipoUsuario);
        }
        return usuario;
    }

    @Override
    public UsuarioDTO buscarUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return convertitEnDTO(usuario);
    }

    @Override
    public UsuarioDTO salvar(UsuarioDTO usuarioDto, Boolean update) {
        log.info("Entra a UsuarioServiceImpl.save. Objeto recibido: {}", usuarioDto);
        Usuario usuario = convertirEnEntidad(usuarioDto);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        if(update)
            return convertitEnDTO(usuarioRepository.save(usuario));
        if(usuarioRepository.existsByCorreo(usuarioDto.getCorreo()))
            throw new UsuarioAlreadyExistsException("correo", usuarioDto.getCorreo());
        if(usuarioRepository.existsByUsuario(usuarioDto.getUsuario()))
            throw new UsuarioAlreadyExistsException("customer-care-center", usuarioDto.getUsuario());
        return convertitEnDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO buscarUsuarioPorUsuario(String usuario, Boolean login) {
        if(login)
            return convertitEnDTO(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new UsuarioNotFoundException(usuario)));
        else{
            Usuario usuarioBD = usuarioRepository.findByUsuario(usuario).orElse(null);
            return usuarioBD == null ? null : convertitEnDTO(usuarioBD);
        }
    }

    @Override
    public void eliminarUsuarioPorId(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioDTO> listarUsuariosPorTipoUsuarioAlias(String alias) {
        return usuarioRepository.findByTipoUsuarioAlias(alias).stream().map(this::convertitEnDTO).toList();
    }
}
