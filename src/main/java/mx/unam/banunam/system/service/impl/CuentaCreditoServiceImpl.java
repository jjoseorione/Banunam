package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.model.CuentaCredito;
import mx.unam.banunam.system.model.MovimientoCredito;
import mx.unam.banunam.system.repository.CuentaCreditoRepository;
import mx.unam.banunam.system.repository.MovimientoCreditoRepository;
import mx.unam.banunam.system.service.CuentaCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaCreditoServiceImpl implements CuentaCreditoService {
    @Autowired
    CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    MovimientoCreditoRepository movimientoCreditoRepository;

    @Override
    public CuentaCredito buscarCuentaCreditoPorNoCuenta(Integer noCuenta) {
        return cuentaCreditoRepository.findById(noCuenta).orElse(null);
    }

    @Override
    public CuentaCredito buscarCuentaCreditoPorNoCliente(Integer noCliente) {
        return cuentaCreditoRepository.findByClienteNoCliente(noCliente).orElse(null);
    }

    @Override
    public CuentaCredito crearCuentaCredito(CuentaCredito cuentaCredito) {
        return cuentaCreditoRepository.save(cuentaCredito);
    }

    @Override
    public List<MovimientoCredito> buscarMovimientosPorNoCuenta(Integer noCuenta) {
        return movimientoCreditoRepository.findByCuentaCreditoNoCuenta(noCuenta);
    }

    //    @Override
//    public List<MovimientoCredito> buscarMovimientosPorNoCuenta(Integer id) {
//        return movimientoCreditoRepository.findByCuentaCreditoNoCuenta(id);
//    }
}
