package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tarjetas_credito")
public class TarjetaCredito {
    @Id
    private String noTarjeta;
    private Character fisicaElectronica;
    private Integer adicional;
    @OneToOne(targetEntity = CuentaCredito.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "noCuenta")
    private CuentaCredito cuentaCredito;
    private LocalDate fechaExp;
    private String cvv;
    private Character estatus;

    @PrePersist
    public void prePersist() {
        if (estatus == null)
            estatus = 'N';
        if (adicional == null)
            adicional = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarjetaCredito that)) return false;
        return Objects.equals(noTarjeta, that.noTarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noTarjeta);
    }
}
