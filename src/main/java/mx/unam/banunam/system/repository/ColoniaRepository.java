package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColoniaRepository extends JpaRepository<Colonia, Integer> {
    List<Colonia> findByCp(String cp);
}
