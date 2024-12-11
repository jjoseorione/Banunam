package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cuentas_prestamo")
public class CuentaPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noCuenta;
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
    @JoinColumn(name = "no_cliente")
    private Cliente cliente;
    private BigDecimal montoSolicitado;
    private Integer noPeriodos;
    private Character periodicidad;
    private Double tasaInteresAnual;
    private Integer periodoActivo;
    private Double saldoRestante;
    private LocalDate fechaAprobacion;

    @PrePersist
    public void prePersist(){
        if (periodoActivo == null)
            periodoActivo = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaPrestamo that)) return false;
        return Objects.equals(noCuenta, that.noCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noCuenta);
    }
}
