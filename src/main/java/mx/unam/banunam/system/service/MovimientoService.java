package mx.unam.banunam.system.service;

import mx.unam.banunam.system.model.MovimientoCredito;
import mx.unam.banunam.system.model.MovimientoDebito;

import java.math.BigDecimal;

public interface MovimientoService {
    MovimientoDebito realizarDepositoDebito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto);
    MovimientoDebito realizarRetiroDebito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto);
    MovimientoCredito realizarDepositoCredito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto);
    MovimientoCredito realizarRetiroCredito(BigDecimal monto, Integer noCuentaDebito, String origenDestino, String concepto);
}
