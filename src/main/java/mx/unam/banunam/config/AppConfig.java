package mx.unam.banunam.config;

import mx.unam.banunam.auth.dto.UsuarioDTO;
import mx.unam.banunam.auth.model.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Cuando mapees de Usuario a UsuarioDTO, tomarás customer-care-center.getTipoUsuario().getAlias() para usuarioDto.setTipoUsuario()
        mm.typeMap(Usuario.class, UsuarioDTO.class).addMappings(mapper ->{
            mapper.map(src -> src.getTipoUsuario().getAlias(), UsuarioDTO::setTipoUsuario);
        });
//        mm.typeMap(CuentaCredito.class, CuentaCreditoDto.class).addMappings(mapper ->{
//            mapper.map(src -> src.getCliente().getNoCliente(), CuentaCreditoDto::setNoCliente);
//        });
        return mm;
    }


}
