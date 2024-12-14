package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.MovimientoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoPrestamoRepository extends JpaRepository<MovimientoPrestamo, Long> {
}
