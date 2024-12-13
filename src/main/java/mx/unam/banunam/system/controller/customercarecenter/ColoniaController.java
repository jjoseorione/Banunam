package mx.unam.banunam.system.controller.customercarecenter;

import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.system.dto.ColoniaDTO;
import mx.unam.banunam.system.service.ColoniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/customer-care-center/colonias/")
@PreAuthorize("hasRole(${perfil.usuario.tipo2})")
public class ColoniaController {
    @Autowired
    ColoniaService coloniaService;

    @GetMapping(path = "{cp}")
    public List<ColoniaDTO> obtenerPorCP(@PathVariable String cp){
        log.info("########## JEEM: Entra a obtenerPorCP");
        log.info("########## JEEM: Cp otorgado: " + cp);
        log.info("########## JEEM: Colonias: " + coloniaService.listarColoniasPorCp(cp));
        return coloniaService.listarColoniasPorCp(cp);
    }
}
