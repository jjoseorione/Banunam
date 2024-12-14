package mx.unam.banunam.system.service;

import mx.unam.banunam.system.model.MovimientoDebito;

import java.math.BigDecimal;

public interface MovimientoService {
    MovimientoDebito realizarDeposito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto);
    MovimientoDebito realizarRetiro(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto);
}
