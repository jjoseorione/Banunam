package mx.unam.banunam.system.service;

import mx.unam.banunam.system.dto.ColoniaDTO;
import mx.unam.banunam.system.model.Colonia;

import java.util.List;

public interface ColoniaService {
    Colonia convertirEnEntidad(ColoniaDTO dto);
    ColoniaDTO convertirEnDTO(Colonia colonia);
    List<ColoniaDTO> listarColoniasPorCp(String cp);
}
