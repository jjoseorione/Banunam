package mx.unam.banunam.auth.usuario.service.impl;

import mx.unam.banunam.auth.usuario.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.usuario.model.TipoUsuario;
import mx.unam.banunam.auth.usuario.repository.TipoUsuarioRepository;
import mx.unam.banunam.auth.usuario.service.TipoUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioService {
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<TipoUsuarioDTO> listarTiposUsuario() {
        return tipoUsuarioRepository.findAll().stream().map(this::convertirEnDTO).collect(Collectors.toList());
    }

    @Override
    public TipoUsuarioDTO convertirEnDTO(TipoUsuario tipoUsuario) {
        return modelMapper.map(tipoUsuario, TipoUsuarioDTO.class);
    }

    @Override
    public TipoUsuario convertirEnEntidad(TipoUsuarioDTO dto) {
        return modelMapper.map(dto, TipoUsuario.class);
    }
}
