package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "colonias")
public class Colonia {
    @Id
    private Integer id_colonia;
    private String nombre;
    private String cp;
    @ManyToOne(targetEntity = Municipio.class)
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;
}
