package mx.unam.banunam.auth.repository;

import mx.unam.banunam.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findAllByOrderByIdUsuarioAsc();
    Optional<Usuario> findByUsuario(String Usuario);
    List<Usuario> findByTipoUsuarioAlias(String alias);
    Boolean existsByUsuario(String usuario);
    Boolean existsByCorreo(String usuario);
}
