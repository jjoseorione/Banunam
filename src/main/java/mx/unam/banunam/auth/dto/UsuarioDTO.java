package mx.unam.banunam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer idUsuario;
    private String usuario;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String contrasena;
    private Byte intentos;
    private Character estatus;
    private String tipoUsuario;
    private LocalDate fechaExpUsuario;
    private LocalDate fechaExpContrasena;
}
