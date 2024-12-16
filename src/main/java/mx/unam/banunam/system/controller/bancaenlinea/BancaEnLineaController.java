package mx.unam.banunam.system.controller.bancaenlinea;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.system.model.*;
import mx.unam.banunam.system.service.CuentaCreditoService;
import mx.unam.banunam.system.service.CuentaDebitoService;
import mx.unam.banunam.system.service.CuentaPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/banca-en-linea/")
@PreAuthorize("hasRole(${perfil.cliente.tipo1})")
public class BancaEnLineaController {
    @Autowired
    CuentaDebitoService cuentaDebitoService;
    @Autowired
    CuentaCreditoService cuentaCreditoService;
    @Autowired
    CuentaPrestamoService cuentaPrestamoService;


    private final String URI_BASE = "/banca-en-linea/";

    @GetMapping("/cuentas")
    public String verCuentas(Model model, HttpSession session){
        Integer noCliente = (Integer) session.getAttribute("noCliente");
        log.info("########## JEEM: El número de cliente es {}", noCliente);

        CuentaDebito cuentaDebito = cuentaDebitoService.buscarCuentaDebitoPorNoCliente(noCliente);
        log.info("########## JEEM: Existe la cuenta de debito: {}", cuentaDebito!=null);
        if(cuentaDebito != null){
            log.info("########## JEEM: Enviamos atributos");
            model.addAttribute("noCuentaDebito", cuentaDebito.getNoCuenta().toString());
            model.addAttribute("saldoCuentaDebito", "$" + cuentaDebito.getSaldo() + " MXN");
        }

        CuentaCredito cuentaCredito = cuentaCreditoService.buscarCuentaCreditoPorNoCliente(noCliente);
        log.info("########## JEEM: Existe la cuenta de crédito: {}", cuentaCredito!=null);
        if(cuentaCredito != null){
            log.info("########## JEEM: Enviamos atributos");
            model.addAttribute("noCuentaCredito", cuentaCredito.getNoCuenta().toString());
            model.addAttribute("saldoCuentaCredito", "$" + cuentaCredito.getSaldoUtilizado() + " MXN");
            model.addAttribute("limCredito", "$" + cuentaCredito.getLimCredito() + " MXN");
        }

        CuentaPrestamo cuentaPrestamo = cuentaPrestamoService.buscarCuentaPrestamoPorNoCliente(noCliente);
        log.info("########## JEEM: Existe la cuenta de préstamo: {}", cuentaPrestamo);
        if(cuentaPrestamo != null){
            log.info("########## JEEM: Enviamos atributos");
            model.addAttribute("noCuentaPrestamo", cuentaPrestamo.getNoCuenta().toString());
            model.addAttribute("saldoCuentaPrestamo", "$" + cuentaPrestamo.getSaldoRestante() + " MXN");
            model.addAttribute("montoAmortizado", "$" + (cuentaPrestamo.getMontoSolicitado().subtract(cuentaPrestamo.getSaldoRestante())) + " MXN");
        }
        return URI_BASE + "banca-cuentas";
    }

    @GetMapping("/movimientos-debito")
    public String movimientosDebito(Model model, HttpSession session){
        Integer noCliente = (Integer) session.getAttribute("noCliente");
        log.info("########## JEEM: El número de cliente es {}", noCliente);

        Integer noCuenta = cuentaDebitoService.buscarCuentaDebitoPorNoCliente(noCliente).getNoCuenta();
        log.info("########## JEEM: La cuenta es {}", noCuenta);
        List<MovimientoDebito> listaMovimientos = cuentaDebitoService.buscarMovimientosPorNoCuenta(noCuenta);
        log.info("########## JEEM: La lista tiene {} movimientos.", listaMovimientos.size());
        model.addAttribute("listaMovimientos", listaMovimientos);
        return URI_BASE + "banca-movimientos-debito";
    }

    @GetMapping("/movimientos-credito")
    public String movimientosCredito(Model model, HttpSession session){
        Integer noCliente = (Integer) session.getAttribute("noCliente");
        log.info("########## JEEM: El número de cliente es {}", noCliente);

        Integer noCuenta = cuentaCreditoService.buscarCuentaCreditoPorNoCliente(noCliente).getNoCuenta();
        log.info("########## JEEM: La cuenta es {}", noCuenta);
        List<MovimientoCredito> listaMovimientos = cuentaCreditoService.buscarMovimientosPorNoCuenta(noCuenta);
        log.info("########## JEEM: La lista tiene {} movimientos.", listaMovimientos.size());
        model.addAttribute("listaMovimientos", listaMovimientos);
        return URI_BASE + "banca-movimientos-credito";
    }

    @GetMapping("/movimientos-prestamo")
    public String movimientosPrestamo(Model model, HttpSession session){
        Integer noCliente = (Integer) session.getAttribute("noCliente");
        log.info("########## JEEM: El número de cliente es {}", noCliente);

        Integer noCuenta = cuentaPrestamoService.buscarCuentaPrestamoPorNoCliente(noCliente).getNoCuenta();
        log.info("########## JEEM: La cuenta es {}", noCuenta);
        List<MovimientoPrestamo> listaMovimientos = cuentaPrestamoService.buscarMovimientosPorNoCuenta(noCuenta);
        log.info("########## JEEM: La lista tiene {} movimientos.", listaMovimientos.size());
        model.addAttribute("listaMovimientos", listaMovimientos);
        return URI_BASE + "banca-movimientos-prestamo";
    }
}
