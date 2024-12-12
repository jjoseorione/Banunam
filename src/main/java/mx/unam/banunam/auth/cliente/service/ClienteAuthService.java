package mx.unam.banunam.auth.cliente.service;

import mx.unam.banunam.auth.cliente.dto.ClienteAuthDTO;
import mx.unam.banunam.system.model.Cliente;

public interface ClienteAuthService {
    ClienteAuthDTO convertirEnDTO(Cliente entidad);
    Cliente convertirEnEntidad(ClienteAuthDTO dto);
    //ClienteAuthDTO buscarClientePorCorreo(String correo);
    ClienteAuthDTO buscarClientePorNoTDD(String noTDD);
}
