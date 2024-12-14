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
@Table(name = "origen_destino_movimientos")
public class OrigenDestinoMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipoOrigenDestino;
    private String descripcion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrigenDestinoMovimiento that)) return false;
        return Objects.equals(tipoOrigenDestino, that.tipoOrigenDestino) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoOrigenDestino, descripcion);
    }
}
