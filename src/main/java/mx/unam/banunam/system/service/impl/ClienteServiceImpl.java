package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.exception.ClienteAlreadyExistsException;
import mx.unam.banunam.system.model.*;
import mx.unam.banunam.system.repository.*;
import mx.unam.banunam.system.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    DomicilioRepository domicilioRepository;
    @Autowired
    CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    CuentaCreditoRepository cuentaCreditorepository;
    @Autowired
    CuentaPrestamoRepository cuentaPrestamoRepository;
    @Qualifier("passwordEncoderUser")
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ColoniaRepository coloniaRepository;
    @Autowired
    MunicipioRepository municipioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Cliente convertirEnEntidad(ClienteDTO dto) {
        //Sólo mapea los campos directos. El domicilio y las cuentas deben llenarse por separado
        return modelMapper.map(dto, Cliente.class);
    }

    @Override
    public ClienteDTO convertirEnDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO buscarClientePorNoCliente(Integer noCliente) {
        Cliente cliente = clienteRepository.findById(noCliente).orElse(null);
        return cliente == null ? null : convertirEnDTO(cliente);
    }

    @Override
    public ClienteDTO salvar(Cliente cliente, Boolean update) {
        cliente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));
        if(update)
            return convertirEnDTO(clienteRepository.save(cliente));
        else{
            if(clienteRepository.existsByRfc(cliente.getRfc()))
                throw new ClienteAlreadyExistsException("RFC", cliente.getRfc());
            if(clienteRepository.existsByCorreo(cliente.getCorreo()))
                throw new ClienteAlreadyExistsException("correo", cliente.getCorreo());

            return convertirEnDTO(clienteRepository.save(cliente));
        }
    }

    @Override
    public Domicilio salvarDomicilio(Domicilio domicilio) {
        return domicilioRepository.save(domicilio);
    }

    @Override
    public Domicilio salvarDomicilio(ClienteDTO clienteDTO, Integer idColonia) {
        Domicilio domicilio = Domicilio.builder()
                .calle(clienteDTO.getCalle())
                .numInterior(clienteDTO.getNumInterior())
                .numExterior((clienteDTO.getNumExterior()))
                .cliente(clienteRepository.findById(clienteDTO.getNoCliente()).orElse(null))
                .colonia(coloniaRepository.findById((idColonia)).orElse(null))
                .build();
        return domicilioRepository.save(domicilio);
    }

    @Override
    public List<ClienteDTO> listarClientesSinCuentaDebito() {
        return clienteRepository.findWhereCuentaDebitoIsNull().stream().map(this::convertirEnDTO).collect(Collectors.toList());
    }
}
