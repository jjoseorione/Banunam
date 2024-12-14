package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.MovimientoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoCreditoRepository extends JpaRepository<MovimientoCredito, Long> {
    List<MovimientoCredito> findByCuentaCreditoNoCuenta(Integer noCuenta);

}
