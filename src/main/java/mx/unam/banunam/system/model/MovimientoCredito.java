package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "movimientos_credito")
public class MovimientoCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folio;
    private LocalDateTime timestampMov;
    private BigDecimal monto;
    @ManyToOne(targetEntity = TipoMovimiento.class)
    @JoinColumn(name = "tipo_mov")
    private TipoMovimiento tipoMov;
    @ManyToOne(targetEntity = CuentaCredito.class)
    @JoinColumn(name = "no_cuenta")
    private CuentaCredito cuentaCredito;
    private String origenDestino;
    @ManyToOne(targetEntity = OrigenDestinoMovimiento.class)
    @JoinColumn(name = "tipo_origen_destino")
    private OrigenDestinoMovimiento tipo_origen_destino;
    private String concepto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovimientoCredito that)) return false;
        return Objects.equals(folio, that.folio);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(folio);
    }
}
