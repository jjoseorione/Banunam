package mx.unam.banunam.system.model;

import jakarta.persistence.*;
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
@Table(name = "cuentas_credito")
public class CuentaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noCuenta;
    private BigDecimal limCredito;
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
    @JoinColumn(name = "noCliente")
    private Cliente cliente;
    private BigDecimal saldoUtilizado;
    private BigDecimal tasaInteresAnual;

    @PrePersist
    public void prePersist(){
        if(limCredito == null)
            limCredito = BigDecimal.ZERO;
        if(saldoUtilizado == null)
            saldoUtilizado = BigDecimal.ZERO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaCredito that)) return false;
        return Objects.equals(noCuenta, that.noCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noCuenta);
    }
}
