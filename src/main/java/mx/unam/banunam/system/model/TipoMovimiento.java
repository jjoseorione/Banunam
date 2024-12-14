package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tipos_movimiento")
public class TipoMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipoMov;
    private String descripcion;
    private Character signoDebito;
    private Character tipoCuenta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoMovimiento that)) return false;
        return Objects.equals(tipoMov, that.tipoMov) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoMov, descripcion);
    }
}
