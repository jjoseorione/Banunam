package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {
    Optional<Domicilio> findByClienteNoCliente(Integer noCliente);
}
