package mx.unam.banunam.system.service;


import mx.unam.banunam.system.model.CuentaPrestamo;

public interface CuentaPrestamoService {
    CuentaPrestamo buscarCuentaPrestamoPorNoCliente(Integer noCliente);
}
