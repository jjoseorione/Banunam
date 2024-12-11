package mx.unam.banunam.auth.repository;

import mx.unam.banunam.auth.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario,Integer> {
    Optional<TipoUsuario> findByAlias(String alias);
    List<TipoUsuario> findAllByOrderByTipoUsuarioAsc();
    List<TipoUsuario> findAllByOrderByAliasAsc();
}