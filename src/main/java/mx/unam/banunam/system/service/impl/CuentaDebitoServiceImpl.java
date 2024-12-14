package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.model.CuentaDebito;
import mx.unam.banunam.system.model.MovimientoDebito;
import mx.unam.banunam.system.model.OrigenDestinoMovimiento;
import mx.unam.banunam.system.model.TipoMovimiento;
import mx.unam.banunam.system.repository.CuentaDebitoRepository;
import mx.unam.banunam.system.repository.MovimientoDebitoRepository;
import mx.unam.banunam.system.repository.OrigenDestinoMovimientoRepository;
import mx.unam.banunam.system.repository.TipoMovimientoRepository;
import mx.unam.banunam.system.service.CuentaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class CuentaDebitoServiceImpl implements CuentaDebitoService {
    @Autowired
    CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    MovimientoDebitoRepository movimientoDebitoRepository;

    @Override
    public CuentaDebito buscarCuentaDebitoPorNoCuenta(Integer id) {
        return cuentaDebitoRepository.findById(id).orElse(null);
    }

    @Override
    public List<MovimientoDebito> buscarMovimientosPorNoCuenta(Integer id) {
        return movimientoDebitoRepository.findByCuentaDebitoNoCuenta(id);
    }

    @Override
    public CuentaDebito crearCuentaDebito(CuentaDebito cuentaDebito) {
        return cuentaDebitoRepository.save(cuentaDebito);
    }
}
