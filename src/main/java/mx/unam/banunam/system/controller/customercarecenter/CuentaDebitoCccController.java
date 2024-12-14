package mx.unam.banunam.system.controller.customercarecenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/customer-care-center/clientes/")
@PreAuthorize("hasRole(${perfil.usuario.tipo2})")
public class CuentaDebitoCccController {
//    @Autowired
//    private CuentaDebitoS
}
