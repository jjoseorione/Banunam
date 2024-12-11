package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.CuentaDebito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaDebitoRepository extends JpaRepository<CuentaDebito,Integer> {
    Optional<CuentaDebito> findByClienteNoCliente(Integer noCliente);
}
