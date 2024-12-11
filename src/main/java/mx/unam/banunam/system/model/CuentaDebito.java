package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cuentas_debito")
public class CuentaDebito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noCuenta")
    private Integer noCuenta;
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
    @JoinColumn(name = "noCliente")
    private Cliente cliente;
    @Digits(integer = 5, fraction = 2)
    @Min(0)
    private BigDecimal saldo;

    @PrePersist
    public void prePersist(){
        if(saldo == null)
            saldo = BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaDebito that)) return false;
        return Objects.equals(noCuenta, that.noCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noCuenta);
    }
}
