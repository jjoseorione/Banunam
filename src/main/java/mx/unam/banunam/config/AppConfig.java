package mx.unam.banunam.config;

import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import mx.unam.banunam.auth.usuario.model.Usuario;
import mx.unam.banunam.system.dto.ClienteDTO;
import mx.unam.banunam.system.dto.ColoniaDTO;
import mx.unam.banunam.system.model.Cliente;
import mx.unam.banunam.system.model.Colonia;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mm.getConfiguration().setSkipNullEnabled(false);

        //Cuando mapees de Usuario a UsuarioDTO, tomarás customer-care-center.getTipoUsuario().getAlias() para usuarioDto.setTipoUsuario()
        mm.typeMap(Usuario.class, UsuarioDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getTipoUsuario().getAlias(), UsuarioDTO::setTipoUsuario);
        });

        mm.typeMap(Cliente.class, ClienteDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getDomicilio().getColonia().getNombre(), ClienteDTO::setColonia);
        });
        mm.typeMap(Cliente.class, ClienteDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getDomicilio().getCalle(), ClienteDTO::setCalle);
        });
        mm.typeMap(Cliente.class, ClienteDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getDomicilio().getNumInterior(), ClienteDTO::setNumInterior);
        });
        mm.typeMap(Cliente.class, ClienteDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getDomicilio().getNumExterior(), ClienteDTO::setNumExterior);
        });
        mm.typeMap(Cliente.class, ClienteDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getCuentaDebito().getNoCuenta(), ClienteDTO::setCuentaDebito);
        });


        mm.typeMap(Colonia.class, ColoniaDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getMunicipio().getNombre(), ColoniaDTO::setMunicipio);
        });
        mm.typeMap(Colonia.class, ColoniaDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getMunicipio().getEstado().getNombre(), ColoniaDTO::setEstado);
        });
//        mm.typeMap(CuentaCredito.class, CuentaCreditoDto.class).addMappings(mapper ->{
//            mapper.map(src -> src.getCliente().getNoCliente(), CuentaCreditoDto::setNoCliente);
//        });
        return mm;
    }


}
