package mx.unam.banunam.auth.usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String usuario;
    private String nombre;
    @Column(name = "apellido_1")
    private String apellido1;
    @Column(name = "apellido_2")
    private String apellido2;
    private String correo;
    private String contrasena;
    private Byte intentos;
    private Character estatus;
    @ManyToOne(targetEntity = TipoUsuario.class)
    @JoinColumn(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;
    private LocalDate fechaExpUsuario;
    private LocalDate fechaExpContrasena;

    @PrePersist
    @PreUpdate
    public void prePersist(){
        if (apellido2 == null)
            apellido2 = "";
        if (intentos == null)
            intentos = 0;
        if (fechaExpUsuario == null)
            fechaExpUsuario = LocalDate.now().plusMonths(6);
        if (fechaExpContrasena == null)
            fechaExpContrasena = LocalDate.now().plusMonths(1);
        if (estatus == null)
            estatus = 'B';
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos == null ? null : intentos.byteValue();
    }

    public Integer getIntentos() {
        return intentos.intValue();
    }
}