package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.model.CuentaPrestamo;
import mx.unam.banunam.system.model.MovimientoPrestamo;
import mx.unam.banunam.system.repository.CuentaPrestamoRepository;
import mx.unam.banunam.system.repository.MovimientoPrestamoRepository;
import mx.unam.banunam.system.service.CuentaPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaPrestamoServiceImpl implements CuentaPrestamoService {
    @Autowired
    CuentaPrestamoRepository cuentaPrestamoRepository;
    @Autowired
    MovimientoPrestamoRepository movimientoPrestamoRepository;

    @Override
    public CuentaPrestamo buscarCuentaPrestamoPorNoCliente(Integer noCliente) {
        return cuentaPrestamoRepository.findByClienteNoCliente(noCliente).orElse(null);
    }

    @Override
    public List<MovimientoPrestamo> buscarMovimientosPorNoCuenta(Integer noCuenta) {
        return movimientoPrestamoRepository.findByCuentaPrestamoNoCuenta(noCuenta);
    }
}
