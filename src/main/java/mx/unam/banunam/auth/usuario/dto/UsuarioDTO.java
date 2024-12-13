package mx.unam.banunam.auth.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer idUsuario;
    @NotBlank(message = "El usuario no puede quedar vacío")
    @Pattern(regexp = "^(ZPT|XMX)\\d{4}$", message = "El usuario debe ser un ZPT ó un XMX")
    private String usuario;
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El nombre sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @NotBlank(message = "El nombre no puede quedar vacío")
    private String nombre;
    @NotBlank(message = "El apellido no puede quedar vacío")
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String apellido1;
    @Pattern(regexp = "^([A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*)*$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String apellido2;
    @NotBlank(message = "El correo no puede quedar vacío")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String correo;
    @NotBlank(message = "La contraseña no puede quedar vacía")
    @Length(message = "La contraseña debe tener entre 5 y 30 caracteres", min=5, max=30)
    private String contrasena;
    private Byte intentos;
    //@Pattern(message = "El estatus sólo puede ser A o B", regexp = "^[AB]$")
    private Character estatus;
    @NotBlank(message = "El tipo de usuario no puede quedar vacío")
    private String tipoUsuario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaExpUsuario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaExpContrasena;
}
