package mx.unam.banunam.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDomicilio;
    @NotBlank(message = "La calle no puede quedar en blanco")
    @Pattern(regexp = "^[A-zÀ-ú0-9]+(\\s?[A-zÀ-ú0-9]+)*$", message = "El nombre sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @Length(min = 1,max = 100, message = "La calle no puede ser mayor a 100 caracteres")
    private String calle;
    @Length(min = 1,max = 30, message = "Número interior no puede ser mayor a 30 caracteres")
    private String numInterior;
    @Length(min = 1,max = 30, message = "Número exterior no puede ser mayor a 30 caracteres")
    private String numExterior;
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
    @JoinColumn(name = "no_cliente")
    private Cliente cliente;
    @ManyToOne(targetEntity = Colonia.class)
    @JoinColumn(name = "id_colonia")
    private Colonia colonia;

    @Override
    public String toString() {
        return "Domicilio{" +
                "idDomicilio=" + idDomicilio +
                ", calle='" + calle + '\'' +
                ", numInterior='" + numInterior + '\'' +
                ", numExterior='" + numExterior + '\'' +
                ", colonia=" + colonia +
                ",\n\tcliente=" + cliente +
                '}';
    }
}
