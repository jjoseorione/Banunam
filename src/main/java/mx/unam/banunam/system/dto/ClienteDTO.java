package mx.unam.banunam.system.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    private Integer noCliente;
    @NotBlank(message = "El nombre no puede quedar vacío")
    @Length(min = 1,max = 50, message = "El nombre no puede ser mayor a 50 caracteres")
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El nombre sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String nombre;
    @NotBlank(message = "El apellido no puede quedar vacío")
    @Length(min = 1,max = 50, message = "El apellido no puede ser mayor a 50 caracteres")
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @Column(name = "apellido_1")
    private String apellido1;
    @Pattern(regexp = "^([A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*)?$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @Length(max = 50, message = "El apellido no puede ser mayor a 50 caracteres")
    @Column(name = "apellido_2")
    private String apellido2;
    @NotBlank(message = "El rfc no puede quedar vacío")
    @Pattern(regexp = "^[A-Z]{4}\\d{6}([0-9A-Z]{3})$", message = "El formato de RFC es incorrecto")
    @Length(min=13, max=13, message = "El RFC debe ser de 13 posiciones")
    private String rfc;
    @NotNull(message = "Debe introducir una fecha de nacimiento")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNac;
    @NotNull(message = "Debe introducir el email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El formato de email es incorrecto")
    private String correo;
    @Length(min=8, max=30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String contrasena;
    @NotNull(message = "Debe introducir un número telefónico")
    @Length(min=5, max=30, message="El número telefónico debe contener entre 5 y 30 posiciones")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "El formato de teléfono es incorrecto")
    private String telefono;
    @NotBlank(message = "La calle no puede quedar en blanco")
    @Pattern(regexp = "^[A-zÀ-ú0-9]+(\\s?[A-zÀ-ú0-9]+)*$", message = "El nombre sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @Length(min = 1,max = 100, message = "La calle no puede ser mayor a 100 caracteres")
    private String calle;
    @Length(min = 1,max = 30, message = "Número interior no puede ser mayor a 30 caracteres")
    private String numInterior;
    @Length(min = 1,max = 30, message = "Número exterior no puede ser mayor a 30 caracteres")
    private String numExterior;
    private String colonia;
    private Integer cuentaDebito;
}
