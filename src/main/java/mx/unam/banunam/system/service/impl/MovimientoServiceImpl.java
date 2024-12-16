package mx.unam.banunam.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.system.model.*;
import mx.unam.banunam.system.repository.*;
import mx.unam.banunam.system.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
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
    @Autowired
    private CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    private MovimientoCreditoRepository movimientoCreditoRepository;


    //ToDo: Estos métodos deben sustituirse por uno general que esté parametrizado para realizar cualquier tipo de movimiento
    @Override
    @Transactional
    public MovimientoDebito realizarDepositoDebito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto) {
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
    public MovimientoDebito realizarRetiroDebito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto) {
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

    @Override
    @Transactional
    public MovimientoCredito realizarDepositoCredito(BigDecimal monto, Integer noCuentaCredito, String origenDestino, String concepto) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(7).orElse(null);
        CuentaCredito cuentaCredito = cuentaCreditoRepository.findById(noCuentaCredito).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(7).orElse(null);

        if(tipoMovimiento == null || cuentaCredito == null || origenDestinoMovimiento == null)
            return null;

        MovimientoCredito mov = MovimientoCredito.builder()
                .folio(null)
                .timestampMov(null)
                .monto(monto)
                .tipoMov(tipoMovimiento)
                .cuentaCredito(cuentaCredito)
                .origenDestino(origenDestino)
                .tipoOrigenDestino(origenDestinoMovimiento)
                .concepto("Depósito en sucursal " + cuentaCredito.getCliente().getDomicilio().getColonia().getNombre())
                .build();
        movimientoCreditoRepository.save(mov);
        cuentaCredito.setSaldoUtilizado(cuentaCredito.getSaldoUtilizado().subtract(mov.getMonto()));
        return mov;
    }

    @Override
    @Transactional
    public MovimientoCredito realizarRetiroCredito(BigDecimal monto, Integer noCuentaCredito, String origenDestino, String concepto) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(6).orElse(null);
        CuentaCredito cuentaCredito = cuentaCreditoRepository.findById(noCuentaCredito).orElse(null);
        OrigenDestinoMovimiento origenDestinoMovimiento = origenDestinoMovimientoRepository.findById(7).orElse(null);

        if(tipoMovimiento == null || cuentaCredito == null || origenDestinoMovimiento == null)
            return null;

        BigDecimal comision = cuentaCredito.getTasaInteresAnual().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        log.info("########## JEEM: MovimientoServiceImpl.realizarRetiroCredito: tasa Interes Anual: {}", cuentaCredito.getTasaInteresAnual());
        log.info("########## JEEM: MovimientoServiceImpl.realizarRetiroCredito: comision: {}", comision);


        MovimientoCredito mov = MovimientoCredito.builder()
                .folio(null)
                .timestampMov(null)
                .monto(monto)
                .tipoMov(tipoMovimiento)
                .cuentaCredito(cuentaCredito)
                .origenDestino(origenDestino)
                .tipoOrigenDestino(origenDestinoMovimiento)
                .concepto("Retiro en sucursal " + cuentaCredito.getCliente().getDomicilio().getColonia().getNombre() + " comisión $" + comision)
                .build();
        movimientoCreditoRepository.save(mov);

        comision = comision.divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP);
        comision = comision.add(BigDecimal.ONE);
        monto = monto.multiply(comision);
        cuentaCredito.setSaldoUtilizado(cuentaCredito.getSaldoUtilizado().add(monto));
        return mov;
    }
}
