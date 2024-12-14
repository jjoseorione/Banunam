package mx.unam.banunam.system.controller.customercarecenter;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.service.UsuarioService;
import mx.unam.banunam.auth.util.PropiedadesPerfiles;
import mx.unam.banunam.security.jwt.JWTTokenProviderUsuario;
import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.Domicilio;
import mx.unam.banunam.system.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/customer-care-center/clientes/")
@PreAuthorize("hasRole(${perfil.usuario.tipo2})")
public class ClienteCccController {
    @Autowired
    private UsuarioService usuarioService;
//    @Autowired
//    @Qualifier("usuarioAuthenticationManager")
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JWTTokenProviderUsuario jwtTokenProviderUsuario;
    @Autowired
    private PropiedadesPerfiles propiedadesPerfiles;
    @Autowired
    private ClienteService clienteService;

    private final String URI_BASE = "/customer-care-center/clientes/";

    @GetMapping("/buscar")
    public String buscarCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        return URI_BASE + "cliente-buscar";
    }

    @PostMapping("/buscar")
    public String buscarClienteAction(@Valid @ModelAttribute("cliente") ClienteDTO cliente, BindingResult result, Model model){
        log.info("########## JEEM: Entra a ClienteCccController.buscarCliente");
        if(result.hasErrors()){
            List<String> listaErrores = new ArrayList<>();
            for(ObjectError e: result.getAllErrors()) {
                listaErrores.add(((FieldError)e).getField());
            }
            log.info("########## JEEM: ClienteCccController.buscarClienteAction: errores={}", listaErrores);
            if(listaErrores.contains("noCliente")){
                model.addAttribute("danger", "Error en la entrada de datos. ");
                return URI_BASE + "cliente-buscar";
            }
        }
        //Cliente clienteBD = clienteService.buscarClientePorNoCliente(cliente.getNoCliente());
        ClienteDTO clienteBD = clienteService.buscarClientePorNoCliente(cliente.getNoCliente());
        log.info("########## JEEM: Cliente a buscar: {}", cliente.getNoCliente());
        log.info("########## JEEM: Cliente encontrado: {}", clienteBD);
        model.addAttribute("clienteBD", clienteBD);
        return URI_BASE + "cliente-buscar";
    }

    @GetMapping("/agregar")
    public String crearCliente(Model model, @ModelAttribute("cliente") ClienteDTO cliente){
        String contrasena1 = "";
        if(cliente==null)
            cliente = new ClienteDTO();
        model.addAttribute("cliente", cliente);
        model.addAttribute("contrasena1", contrasena1);
        model.addAttribute("idColonia", 0);
        return URI_BASE + "cliente-agregar";
    }

    @PostMapping("/agregar")
    public String validaAltaCliente(@Valid @ModelAttribute("cliente") ClienteDTO clienteRecibido, BindingResult result, String contrasena1,
                                    String idColonia, Model model, RedirectAttributes flash){

        log.info("########## JEEM: Información recibida:");
        log.info("########## JEEM: {}: ", clienteRecibido);
        log.info("########## JEEM: {}: ", contrasena1);
        log.info("########## JEEM: {}: ", idColonia);
        String errorMsg = "Error en la entrada de datos. Valide lo siguiente: ";
        List<String> listaErrores = new ArrayList<>();
        if(result.hasErrors()){
            for(ObjectError e: result.getAllErrors()){
                listaErrores.add(e.getDefaultMessage());
                System.out.println(listaErrores);
            }

            flash.addFlashAttribute("danger", errorMsg);
            flash.addFlashAttribute("listaErrores", listaErrores);
            flash.addFlashAttribute("cliente", clienteRecibido);
            return "redirect:" + URI_BASE + "agregar";
        }
        if(!clienteRecibido.getContrasena().equals(contrasena1)){
            listaErrores.add("Las contraseñas no coinciden");
            flash.addFlashAttribute("danger", errorMsg);
            flash.addFlashAttribute("listaErrores", listaErrores);
            flash.addFlashAttribute("cliente", clienteRecibido);
            return "redirect:" + URI_BASE + "agregar";
        }

        ClienteDTO clienteCreado = clienteService.salvar(clienteService.convertirEnEntidad(clienteRecibido), false);
        clienteRecibido.setNoCliente(clienteCreado.getNoCliente());
        clienteService.salvarDomicilio(clienteRecibido, Integer.parseInt(idColonia));
//        ClienteDTO clienteCreado = clienteService.buscarClientePorNoCliente()

        model.addAttribute(clienteCreado);
        model.addAttribute("success", "Cliente creado");
        return URI_BASE + "cliente-agregar";
    }
}
