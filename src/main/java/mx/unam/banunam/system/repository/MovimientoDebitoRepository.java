package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.MovimientoDebito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoDebitoRepository extends JpaRepository<MovimientoDebito, Long> {
    List<MovimientoDebito> findByCuentaDebitoNoCuenta(Integer noCuenta);
}
