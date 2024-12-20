package mx.unam.banunam.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class RootController {
    @GetMapping("/")
    public String raiz() {
        log.info("########## JEEM: RootController: Entra a raíz. Se muestra una página de presentación");
        return "home";
    }
}
