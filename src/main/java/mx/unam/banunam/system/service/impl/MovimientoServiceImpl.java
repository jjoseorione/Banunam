package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.model.CuentaDebito;
import mx.unam.banunam.system.model.MovimientoDebito;
import mx.unam.banunam.system.model.OrigenDestinoMovimiento;
import mx.unam.banunam.system.model.TipoMovimiento;
import mx.unam.banunam.system.repository.CuentaDebitoRepository;
import mx.unam.banunam.system.repository.MovimientoDebitoRepository;
import mx.unam.banunam.system.repository.OrigenDestinoMovimientoRepository;
import mx.unam.banunam.system.repository.TipoMovimientoRepository;
import mx.unam.banunam.system.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    private OrigenDestinoMovimientoRepository origenDestinoMovimientoRepository;
    @Autowired
    private CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    private MovimientoDebitoRepository movimientoDebitoRepository;

    @Override
    @Transactional
    public MovimientoDebito realizarDeposito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(3).orElse(null);
        CuentaDebito cuentaDebito = cuentaDebitoRepository.findById(noCuentaDebito).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(7).orElse(null);

        if(tipoMovimiento == null || cuentaDebito == null || origenDestinoMovimiento == null)
            return null;

        MovimientoDebito mov = MovimientoDebito.builder()
                .folio(null)
                .timestampMov(null)
                .monto(monto)
                .tipoMov(tipoMovimiento)
                .cuentaDebito(cuentaDebito)
                .origenDestino(origenDestino)
                .tipoOrigenDestino(origenDestinoMovimiento)
                .concepto("Depósito en sucursal " + cuentaDebito.getCliente().getDomicilio().getColonia().getNombre())
                .build();
        movimientoDebitoRepository.save(mov);
        cuentaDebito.setSaldo(cuentaDebito.getSaldo().add(mov.getMonto()));
        return mov;
    }

    @Override
    @Transactional
    public MovimientoDebito realizarRetiro(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(6).orElse(null);
        CuentaDebito cuentaDebito = cuentaDebitoRepository.findById(noCuentaDebito).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(7).orElse(null);

        if(tipoMovimiento == null || cuentaDebito == null || origenDestinoMovimiento == null)
            return null;

        MovimientoDebito mov = MovimientoDebito.builder()
                .folio(null)
                .timestampMov(null)
                .monto(monto)
                .tipoMov(tipoMovimiento)
                .cuentaDebito(cuentaDebito)
                .origenDestino(origenDestino)
                .tipoOrigenDestino(origenDestinoMovimiento)
                .concepto("Retiro en sucursal " + cuentaDebito.getCliente().getDomicilio().getColonia().getNombre())
                .build();
        movimientoDebitoRepository.save(mov);
        cuentaDebito.setSaldo(cuentaDebito.getSaldo().subtract(mov.getMonto()));
        return mov;
    }
}
