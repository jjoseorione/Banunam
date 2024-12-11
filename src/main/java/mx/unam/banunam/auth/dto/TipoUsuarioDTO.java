package mx.unam.banunam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TipoUsuarioDTO {
    private Integer tipoUsuario;
    private String alias;
    private String descripcion;
}
