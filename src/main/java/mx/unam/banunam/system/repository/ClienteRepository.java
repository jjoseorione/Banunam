package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente,Integer>, PagingAndSortingRepository<Cliente, Integer> {
    Optional<Cliente> findByCorreo(String correo);

    @Query(value = "SELECT td.cuentaDebito.cliente FROM TarjetaDebito td WHERE td.noTarjeta = :noTarjetaDebito")
    Optional<Cliente> findByNoTarjetaDebito(String noTarjetaDebito);
}
