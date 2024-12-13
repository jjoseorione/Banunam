package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.dto.ColoniaDTO;
import mx.unam.banunam.system.model.Colonia;
import mx.unam.banunam.system.repository.ColoniaRepository;
import mx.unam.banunam.system.repository.EstadoRepository;
import mx.unam.banunam.system.repository.MunicipioRepository;
import mx.unam.banunam.system.service.ColoniaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColoniaServiceImpl implements ColoniaService {
    @Autowired
    private ColoniaRepository coloniaRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Colonia convertirEnEntidad(ColoniaDTO dto) {
        Colonia colonia = modelMapper.map(dto, Colonia.class);
        Colonia coloniaBD = coloniaRepository.findById(dto.getId_colonia()).orElse(null);
        if(colonia.getId_colonia().equals(coloniaBD.getId_colonia())  &&
                colonia.getNombre().equals(coloniaBD.getNombre())  &&
                colonia.getCp().equals(coloniaBD.getCp()))
            colonia.setMunicipio(coloniaBD.getMunicipio());
        return colonia;
    }

    @Override
    public ColoniaDTO convertirEnDTO(Colonia colonia) {
        return modelMapper.map(colonia, ColoniaDTO.class);
    }

    @Override
    public List<ColoniaDTO> listarColoniasPorCp(String cp) {
        return coloniaRepository.findByCp(cp).stream().map(this::convertirEnDTO).collect(Collectors.toList());
    }
}
