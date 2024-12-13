package mx.unam.banunam.system.service;


import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente convertirEnEntidad(ClienteDTO dto);
    ClienteDTO convertirEnDTO(Cliente cliente);
    ClienteDTO buscarClientePorNoCliente(Integer noCliente);
    ClienteDTO salvar(ClienteDTO cliente);
    List<Cliente> listarClientes();
}
