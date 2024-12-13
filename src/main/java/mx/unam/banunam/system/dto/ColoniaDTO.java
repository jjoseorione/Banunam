package mx.unam.banunam.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColoniaDTO {
    private Integer id_colonia;
    private String nombre;
    private String cp;
    private String municipio;
    private String estado;
}
