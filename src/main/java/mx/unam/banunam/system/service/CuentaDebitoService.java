package mx.unam.banunam.system.service;

import mx.unam.banunam.system.model.CuentaDebito;
import mx.unam.banunam.system.model.MovimientoDebito;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaDebitoService {
    CuentaDebito buscarCuentaDebitoPorNoCuenta(Integer id);
    List<MovimientoDebito> buscarMovimientosPorNoCuenta(Integer id);
    CuentaDebito crearCuentaDebito(CuentaDebito cuentaDebito);
}
