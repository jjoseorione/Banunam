package mx.unam.banunam.system.controller.customercarecenter;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.model.CuentaCredito;
import mx.unam.banunam.system.model.MovimientoCredito;
import mx.unam.banunam.system.model.MovimientoDebito;
import mx.unam.banunam.system.service.ClienteService;
import mx.unam.banunam.system.service.CuentaCreditoService;
import mx.unam.banunam.system.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/customer-care-center/cuentas-credito/")
@PreAuthorize("hasRole(${perfil.usuario.tipo2})")
public class CuentaCreditoCccController {
    @Autowired
    private CuentaCreditoService cuentaCreditoService;
    @Autowired
    private MovimientoService movimientoService;
    @Autowired
    private ClienteService clienteService;

    private final String URI_BASE = "/customer-care-center/cuentas-credito/";

    @GetMapping("/buscar")
    public String buscarCuentaCredito(Model model){
        model.addAttribute("cuentaCredito", new CuentaCredito());
        return URI_BASE + "cuenta-credito-buscar";
    }

    @PostMapping("/buscar")
    public String buscarCuentaCredito(@Valid @ModelAttribute("cuentaCredito") CuentaCredito cuentaCredito, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("danger", "Error en la entrada de datos.");
            return URI_BASE + "cuenta-credito-buscar";
        }
        log.info("########## JEEM: Número a buscar: {}", cuentaCredito.getNoCuenta());
        CuentaCredito cuentaCreditoBD = cuentaCreditoService.buscarCuentaCreditoPorNoCuenta(cuentaCredito.getNoCuenta());
        log.info("########## JEEM: Cuenta encontrada: {}", cuentaCreditoBD);
        model.addAttribute("cuentaCreditoBD", cuentaCreditoBD);
        return URI_BASE + "cuenta-credito-buscar";
    }

    @GetMapping("/crear")
    public String crearCuentaCredito(Model model, CuentaCredito cuentaCredito){
        List<ClienteDTO> listaClientes = clienteService.listarClientesSinCuentaCredito();
        if(cuentaCredito == null)
            cuentaCredito = new CuentaCredito();
        if(listaClientes.isEmpty())
            model.addAttribute("danger", "Todos los clientes tienen ya una cuenta de crédito");
        else
            model.addAttribute("listaClientes", listaClientes);
        model.addAttribute("cuentaCredito", cuentaCredito);
        return URI_BASE + "cuenta-credito-agregar";
    }

    @PostMapping("/crear")
    public String validaCuenta(@Valid @ModelAttribute("cuentaCredito") CuentaCredito cuentaCredito, BindingResult result,
                                    Model model, RedirectAttributes flash){
        String errorMsg = "Error en la entrada de datos. Valide lo siguiente: ";
        List<String> listaErrores = new ArrayList<>();
        log.info("########## JEEM: Cuenta recibida: {}", cuentaCredito);
        if(result.hasErrors()){
            for(ObjectError e: result.getAllErrors()){
                listaErrores.add(e.getDefaultMessage());
                log.info("########## JEEM: Lista de errores: {}",listaErrores);
            }

            flash.addFlashAttribute("danger", errorMsg);
            flash.addFlashAttribute("listaErrores", listaErrores);
            flash.addFlashAttribute("cuentaCredito", cuentaCredito);
            return "redirect:" + URI_BASE + "crear";
        }

        CuentaCredito cuentaCreada = cuentaCreditoService.crearCuentaCredito(cuentaCredito);
        model.addAttribute(cuentaCreada);
        model.addAttribute("success", "Cuenta " + cuentaCreada.getNoCuenta() + " creada al cliente " + cuentaCreada.getCliente().getNoCliente());
        return URI_BASE + "cuenta-credito-agregar";
    }


    @GetMapping("/deposito")
    public String deposito(Model model){
        model.addAttribute("cuentaCredito", new CuentaCredito());
        return URI_BASE + "cuenta-credito-deposito";
    }

    @PostMapping("/deposito")
    public String deposito(@Valid @ModelAttribute("cuentaCredito") CuentaCredito cuentaCredito, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("danger", "Error en la entrada de datos.");
            return URI_BASE + "cuenta-credito-buscar";
        }
        log.info("########## JEEM: Número a buscar: {}", cuentaCredito.getNoCuenta());
        CuentaCredito cuentaCreditoBD = cuentaCreditoService.buscarCuentaCreditoPorNoCuenta(cuentaCredito.getNoCuenta());
        log.info("########## JEEM: Cuenta encontrada: {}", cuentaCreditoBD);
        model.addAttribute("cuentaCreditoBD", cuentaCreditoBD);
        model.addAttribute("deposito", new MovimientoCredito());
        return URI_BASE + "cuenta-credito-deposito";
    }

    @PostMapping("/confirmarDeposito")
    public String confirmarDeposito(@Valid @ModelAttribute("movimientoCredito") MovimientoCredito movimientoCredito, BindingResult result,
                                    Integer noCuenta, Model model, HttpSession session, RedirectAttributes flash){
        log.info("########## JEEM: Información recibida:");
        log.info("########## JEEM: {}", movimientoCredito);
        log.info("########## JEEM: {}", noCuenta);
        List<String> listaErrores = new ArrayList<>();
        String errorMsg = "";
        if(result.hasErrors()){
            for(ObjectError e: result.getAllErrors()){
                if(e.getDefaultMessage().contains("monto"))
                    errorMsg = "Error en la entrada de datos. El monto debe estar ser un número positivo con dos decimales";
            }
        }
        if(!Objects.equals(errorMsg, "")){
            flash.addFlashAttribute("danger", errorMsg);
            return "redirect:" + URI_BASE + "deposito";
        }

        MovimientoCredito movimientoCreado = movimientoService.realizarDepositoCredito(movimientoCredito.getMonto(), noCuenta, (String) session.getAttribute("usuarioFirmado"), "Depósito en sucursal");
        if(movimientoCreado == null){
            errorMsg = "Error al realizar el depósito";
            flash.addFlashAttribute("danger", errorMsg);
            return "redirect:" + URI_BASE + "deposito";
        }
        String successMsg = "Se realizó el depósito por " + movimientoCredito.getMonto() + " a la cuenta " + noCuenta;
        flash.addFlashAttribute("success", successMsg);
        return "redirect:" + URI_BASE + "deposito";
    }

    @GetMapping("/retiro")
    public String retiro(Model model){
        model.addAttribute("cuentaCredito", new CuentaCredito());
        return URI_BASE + "cuenta-credito-retiro";
    }

    @PostMapping("/retiro")
    public String retiro(@Valid @ModelAttribute("cuentaCredito") CuentaCredito cuentaCredito, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("danger", "Error en la entrada de datos.");
            return URI_BASE + "cuenta-credito-buscar";
        }
        log.info("########## JEEM: Número a buscar: {}", cuentaCredito.getNoCuenta());
        CuentaCredito cuentaCreditoBD = cuentaCreditoService.buscarCuentaCreditoPorNoCuenta(cuentaCredito.getNoCuenta());
        log.info("########## JEEM: Cuenta encontrada: {}", cuentaCreditoBD);
        model.addAttribute("cuentaCreditoBD", cuentaCreditoBD);
        model.addAttribute("retiro", new MovimientoCredito());
        return URI_BASE + "cuenta-credito-retiro";
    }

    @PostMapping("/confirmarRetiro")
    public String confirmarRetiro(@Valid @ModelAttribute("movimientoDebito") MovimientoDebito movimientoDebito, BindingResult result,
                                    Integer noCuenta, Model model, HttpSession session, RedirectAttributes flash){
        log.info("########## JEEM: Información recibida:");
        log.info("########## JEEM: {}", movimientoDebito);
        log.info("########## JEEM: {}", noCuenta);
        List<String> listaErrores = new ArrayList<>();
        String errorMsg = "";
        if(result.hasErrors()){
            for(ObjectError e: result.getAllErrors()){
                if(e.getDefaultMessage().contains("monto"))
                    errorMsg = "Error en la entrada de datos. El monto debe estar ser un número positivo con dos decimales";
            }
        }
        if(!Objects.equals(errorMsg, "")){
            flash.addFlashAttribute("danger", errorMsg);
            return "redirect:" + URI_BASE + "retiro";
        }

        MovimientoCredito movimientoCreado = movimientoService.realizarRetiroCredito(movimientoDebito.getMonto(), noCuenta, (String) session.getAttribute("usuarioFirmado"), "Retiro en sucursal");
        if(movimientoCreado == null){
            errorMsg = "Error al realizar el retiro";
            flash.addFlashAttribute("danger", errorMsg);
            return "redirect:" + URI_BASE + "retiro";
        }
        String successMsg = "Se realizó el retiro por " + movimientoDebito.getMonto() + " a la cuenta " + noCuenta;
        flash.addFlashAttribute("success", successMsg);
        return "redirect:" + URI_BASE + "retiro";
    }

    @GetMapping("/movimientos")
    public String verMovimientos(Model model){
        model.addAttribute("cuentaCredito", new CuentaCredito());
        return URI_BASE + "movimientos-buscar";
    }

    @PostMapping("/movimientos")
    public String verMovimientos(@Valid @ModelAttribute("cuentaCredito") CuentaCredito cuentaCredito, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("danger", "Error en la entrada de datos. ");
            return URI_BASE + "movimientos-buscar";
        }
        System.out.println(cuentaCredito.getNoCuenta());
        List<MovimientoCredito> listaMovimientos = cuentaCreditoService.buscarMovimientosPorNoCuenta(cuentaCredito.getNoCuenta());
        System.out.println(listaMovimientos);
        model.addAttribute("listaMovimientos", listaMovimientos);
        return URI_BASE + "movimientos-buscar";
    }

}
