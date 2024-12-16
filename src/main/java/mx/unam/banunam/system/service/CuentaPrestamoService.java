package mx.unam.banunam.system.service;


import mx.unam.banunam.system.model.CuentaPrestamo;
import mx.unam.banunam.system.model.MovimientoPrestamo;

import java.util.List;

public interface CuentaPrestamoService {
    CuentaPrestamo buscarCuentaPrestamoPorNoCliente(Integer noCliente);
    List<MovimientoPrestamo> buscarMovimientosPorNoCuenta(Integer noCuenta);
}
