package mx.unam.banunam.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.dto.UsuarioDTO;
import mx.unam.banunam.auth.exception.UsuarioAlreadyExistsException;
import mx.unam.banunam.auth.exception.UsuarioNotFoundException;
import mx.unam.banunam.auth.model.TipoUsuario;
import mx.unam.banunam.auth.model.Usuario;
import mx.unam.banunam.auth.repository.TipoUsuarioRepository;
import mx.unam.banunam.auth.repository.UsuarioRepository;
import mx.unam.banunam.auth.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    ModelMapper modelMapper;

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
    public UsuarioDTO salvar(UsuarioDTO usuarioDto) {
        log.info("Entra a UsuarioServiceImpl.save. Objeto recibido: {}", usuarioDto);
        if(usuarioRepository.existsByCorreo(usuarioDto.getCorreo()))
            throw new UsuarioAlreadyExistsException("correo", usuarioDto.getCorreo());
        if(usuarioRepository.existsByUsuario(usuarioDto.getUsuario()))
            throw new UsuarioAlreadyExistsException("customer-care-center", usuarioDto.getUsuario());
        Usuario usuario = convertirEnEntidad(usuarioDto);
        return convertitEnDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO buscarUsuarioPorUsuario(String usuario) {
        return convertitEnDTO(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new UsuarioNotFoundException(usuario)));
    }


}
