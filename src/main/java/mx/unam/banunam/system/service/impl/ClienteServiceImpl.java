package mx.unam.banunam.system.service.impl;

import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.model.*;
import mx.unam.banunam.system.repository.*;
import mx.unam.banunam.system.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    ColoniaRepository coloniaRepository;
    @Autowired
    MunicipioRepository municipioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Cliente convertirEnEntidad(ClienteDTO dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Domicilio domicilio = domicilioRepository.findByClienteNoCliente(dto.getNoCliente()).orElse(null);
        CuentaDebito cuentaDebito = cuentaDebitoRepository.findByClienteNoCliente(dto.getNoCliente()).orElse(null);
        CuentaCredito cuentaCredito = cuentaCreditorepository.findByClienteNoCliente(dto.getNoCliente()).orElse(null);
        CuentaPrestamo cuentaPrestamo = cuentaPrestamoRepository.findByClienteNoCliente(dto.getNoCliente()).orElse(null);
        cliente.setDomicilio(domicilio);
        cliente.setCuentaDebito(cuentaDebito);
        cliente.setCuentaCredito(cuentaCredito);
        cliente.setCuentaPrestamo(cuentaPrestamo);

        return cliente;
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
    public ClienteDTO salvar(ClienteDTO cliente) {
        return null;
    }

    @Override
    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }
}
