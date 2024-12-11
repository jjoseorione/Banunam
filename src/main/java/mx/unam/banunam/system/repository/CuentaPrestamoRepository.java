package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.CuentaPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CuentaPrestamoRepository extends JpaRepository<CuentaPrestamo, Integer> {
    Optional<CuentaPrestamo> findByCliente(Cliente cliente);
}
