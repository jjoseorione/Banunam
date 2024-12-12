package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.TarjetaDebito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarjetaDebitoRepository extends JpaRepository<TarjetaDebito, String> {
    List<TarjetaDebito> findByCuentaDebitoNoCuenta(Integer noCuenta);
}