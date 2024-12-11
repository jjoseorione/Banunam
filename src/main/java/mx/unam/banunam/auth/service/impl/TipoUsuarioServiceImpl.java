package mx.unam.banunam.auth.service.impl;

import mx.unam.banunam.auth.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.model.TipoUsuario;
import mx.unam.banunam.auth.service.TipoUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioService {
    @Autowired
    ModelMapper modelMapper;

    @Override
    public TipoUsuarioDTO convertirEnDTO(TipoUsuario tipoUsuario) {
        return modelMapper.map(tipoUsuario, TipoUsuarioDTO.class);
    }

    @Override
    public TipoUsuario convertirEnEntidad(TipoUsuarioDTO dto) {
        return modelMapper.map(dto, TipoUsuario.class);
    }
}
