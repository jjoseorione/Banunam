package mx.unam.banunam.system.service;


import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.Domicilio;

import java.util.List;

public interface ClienteService {
    Cliente convertirEnEntidad(ClienteDTO dto);
    ClienteDTO convertirEnDTO(Cliente cliente);
    ClienteDTO buscarClientePorNoCliente(Integer noCliente);
    ClienteDTO salvar(Cliente cliente, Boolean update);
    Domicilio salvarDomicilio(Domicilio domicilio);
    Domicilio salvarDomicilio(ClienteDTO clienteDTO, Integer idColonia);
    List<ClienteDTO> listarClientesSinCuentaDebito();
    List<ClienteDTO> listarClientesSinCuentaCredito();
}
