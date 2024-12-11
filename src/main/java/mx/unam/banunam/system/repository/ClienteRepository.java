package mx.unam.banunam.system.repository;

import mx.unam.banunam.system.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteRepository extends CrudRepository<Cliente,Integer>, PagingAndSortingRepository<Cliente, Integer> {

}
