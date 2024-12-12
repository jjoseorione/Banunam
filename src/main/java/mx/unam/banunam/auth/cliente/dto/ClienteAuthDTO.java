package mx.unam.banunam.auth.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteAuthDTO {
    private Integer noCliente;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String contrasena;
    private Set<String> tarjetasDebito;
}
