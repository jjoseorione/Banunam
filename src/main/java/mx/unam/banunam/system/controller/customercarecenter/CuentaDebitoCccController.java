package mx.unam.banunam.system.controller.customercarecenter;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.system.model.CuentaDebito;
import mx.unam.banunam.system.model.MovimientoDebito;
import mx.unam.banunam.system.service.CuentaDebitoService;
import mx.unam.banunam.system.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/customer-care-center/cuentas-debito/")
@PreAuthorize("hasRole(${perfil.usuario.tipo2})")
public class CuentaDebitoCccController {
    @Autowired
    private CuentaDebitoService cuentaDebitoService;
    @Autowired
    private MovimientoService movimientoService;

    private final String URI_BASE = "/customer-care-center/cuentas-debito/";

    @GetMapping("/buscar")
    public String buscarCuentaDebito(Model model){
        model.addAttribute("cuentaDebito", new CuentaDebito());
        return URI_BASE + "cuenta-debito-buscar";
    }

    @PostMapping("/buscar")
    public String buscarCuentaDebito(@Valid @ModelAttribute("cuentaDebito") CuentaDebito cuentaDebito, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("danger", "Error en la entrada de datos.");
            return URI_BASE + "cuenta-debito-buscar";
        }
        log.info("########## JEEM: Número a buscar: {}", cuentaDebito.getNoCuenta());
        CuentaDebito cuentaDebitoBD = cuentaDebitoService.buscarCuentaDebitoPorNoCuenta(cuentaDebito.getNoCuenta());
        log.info("########## JEEM: Cuenta encontrada: {}", cuentaDebitoBD);
        model.addAttribute("cuentaDebitoBD", cuentaDebitoBD);
        return URI_BASE + "cuenta-debito-buscar";
    }

    @GetMapping("/deposito")
    public String deposito(Model model){
        model.addAttribute("cuentaDebito", new CuentaDebito());
        return URI_BASE + "cuenta-debito-deposito";
    }

    @PostMapping("/deposito")
    public String deposito(@Valid @ModelAttribute("cuentaDebito") CuentaDebito cuentaDebito, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("danger", "Error en la entrada de datos.");
            return URI_BASE + "cuenta-debito-buscar";
        }
        log.info("########## JEEM: Número a buscar: {}", cuentaDebito.getNoCuenta());
        CuentaDebito cuentaDebitoBD = cuentaDebitoService.buscarCuentaDebitoPorNoCuenta(cuentaDebito.getNoCuenta());
        log.info("########## JEEM: Cuenta encontrada: {}", cuentaDebitoBD);
        model.addAttribute("cuentaDebitoBD", cuentaDebitoBD);
        model.addAttribute("deposito", new MovimientoDebito());
        return URI_BASE + "cuenta-debito-deposito";
    }

    @PostMapping("/confirmarDeposito")
    public String confirmarDeposito(@Valid @ModelAttribute("movimientoDebito") MovimientoDebito movimientoDebito, BindingResult result,
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
            return "redirect:" + URI_BASE + "deposito";
        }

        MovimientoDebito movimientoCreado = movimientoService.realizarDeposito(movimientoDebito.getMonto(), noCuenta, (String) session.getAttribute("usuarioFirmado"), "Depósito en sucursal");
        if(movimientoCreado == null){
            errorMsg = "Error al realizar el depósito";
            flash.addFlashAttribute("danger", errorMsg);
            return "redirect:" + URI_BASE + "deposito";
        }
        String successMsg = "Se realizó el depósito por " + movimientoDebito.getMonto() + " a la cuenta " + noCuenta;
        flash.addFlashAttribute("success", successMsg);
        return "redirect:" + URI_BASE + "deposito";
    }
}
