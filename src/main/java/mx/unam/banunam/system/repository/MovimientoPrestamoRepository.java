package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.MovimientoCredito;
import mx.unam.banunam.system.model.MovimientoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoPrestamoRepository extends JpaRepository<MovimientoPrestamo, Long> {
    List<MovimientoPrestamo> findByCuentaPrestamoNoCuenta(Integer noCuenta);
}
