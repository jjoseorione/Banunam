package mx.unam.banunam.auth.cliente.service.impl;

import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.cliente.dto.ClienteAuthDTO;
import mx.unam.banunam.auth.cliente.service.ClienteAuthService;
import mx.unam.banunam.auth.exception.ClienteNotFoundException;
import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.TarjetaDebito;
import mx.unam.banunam.system.repository.ClienteRepository;
import mx.unam.banunam.system.repository.TarjetaDebitoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteAuthServiceImpl implements ClienteAuthService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    TarjetaDebitoRepository tarjetaDebitoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ClienteAuthDTO convertirEnDTO(Cliente entidad) {
        ClienteAuthDTO dto = modelMapper.map(entidad, ClienteAuthDTO.class);
        Set<String> tdds = tarjetaDebitoRepository.findByCuentaDebitoNoCuenta(entidad.getCuentaDebito().getNoCuenta())
                .stream().map(TarjetaDebito::getNoTarjeta)
                .collect(Collectors.toSet());
        dto.setTarjetasDebito(tdds);
        return dto;
    }

    @Override
    public Cliente convertirEnEntidad(ClienteAuthDTO dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Cliente clienteBD = clienteRepository.findById(dto.getNoCliente()).orElse(null);
        cliente.setRfc(clienteBD.getRfc());
        cliente.setFechaNac(clienteBD.getFechaNac());
        cliente.setTelefono(clienteBD.getTelefono());
        return cliente;
    }

//    @Override
//    public ClienteAuthDTO buscarClientePorCorreo(String correo) {
//        return convertirEnDTO(clienteRepository.findByCorreo(correo).orElseThrow(() -> new ClienteNotFoundException(correo)));
//    }

    @Override
    public ClienteAuthDTO buscarClientePorNoTDD(String noTDD) {
        return convertirEnDTO(clienteRepository.findByNoTarjetaDebito(noTDD).orElseThrow(() -> new ClienteNotFoundException(noTDD)));
    }
}
