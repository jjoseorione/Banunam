package mx.unam.banunam.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class HelloWorldController {
    @GetMapping("/welcome")
    public String welcome() {
        log.info("Entra a /auth/welcome");
        return "Hello World Spring Security!";
    }
}