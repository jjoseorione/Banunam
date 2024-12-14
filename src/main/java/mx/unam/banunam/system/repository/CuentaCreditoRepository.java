package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.CuentaCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaCreditoRepository extends JpaRepository<CuentaCredito, Integer> {
    Optional<CuentaCredito> findByClienteNoCliente(Integer noCliente);
}
