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
@Table(name = "municipios")
public class Municipio {
    @Id
    private Integer idMunicipio;
    private String nombre;
    private Integer cveMunicipio;
    @ManyToOne(targetEntity = Estado.class)
    @JoinColumn(name = "id_estado")
    private Estado estado;
}
