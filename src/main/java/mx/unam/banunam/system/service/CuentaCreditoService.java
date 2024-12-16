package mx.unam.banunam.system.service;

import mx.unam.banunam.system.model.CuentaCredito;
import mx.unam.banunam.system.model.MovimientoCredito;

import java.util.List;

public interface CuentaCreditoService {
    CuentaCredito buscarCuentaCreditoPorNoCuenta(Integer id);
//    CuentaCredito buscarCuentaCreditoPorNoCliente(Integer id);
//    List<MovimientoCredito> buscarMovimientosPorNoCuenta(Integer id);
    CuentaCredito buscarCuentaCreditoPorNoCliente(Integer noCliente);
    CuentaCredito crearCuentaCredito(CuentaCredito cuentaCredito);
    List<MovimientoCredito> buscarMovimientosPorNoCuenta(Integer noCuenta);
}