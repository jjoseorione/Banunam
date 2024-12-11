package mx.unam.banunam.security;


import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.repository.UsuarioRepository;
import mx.unam.banunam.security.jwt.JWTAuthenticationFilter;
import mx.unam.banunam.security.jwt.JWTTokenProvider;
import mx.unam.banunam.security.logout.CustomLogoutSuccessHandler;
import mx.unam.banunam.security.service.AuthenticationProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter{
        @Autowired
        private JWTTokenProvider tokenProvider;
        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
            log.info("########## JEEM: Inicialización de Bean SecurityFilterChain1");
            http
                    .securityMatcher("/user-administration/**")
                    .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers("/css/**", "/favicon.ico", "/user-administration/", "/user-administration/token").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(login -> login
                            .loginPage("/user-administration/login")
                            .successForwardUrl("/user-administration/login_success_handler")
                            .failureForwardUrl("/user-administration/login_failure_handler")
                            .permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/user-administration/doLogout")
                            .logoutSuccessUrl("/index")
                            .deleteCookies("JSESSIONID") //NEW Cookies to clear
                            .logoutSuccessHandler(customLogoutSuccessHandler)
                            .clearAuthentication(true)
                            .invalidateHttpSession(true))
                    .addFilterAfter(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            ;
            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter{
        @Autowired
        private JWTTokenProvider tokenProvider;
        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
            log.info("########## JEEM: Inicialización de Bean SecurityFilterChain2");
            http
                    .securityMatcher("/customer-care-center/**")
                    .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers("/css/**", "/favicon.ico", "/customer-care-center/", "/customer-care-center/token").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(login -> login
                            .loginPage("/customer-care-center/login")
                            .successForwardUrl("/customer-care-center/login_success_handler")
                            .failureForwardUrl("/customer-care-center/login_failure_handler")
                            .permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/customer-care-center/doLogout")
                            .logoutSuccessUrl("/index")
                            .deleteCookies("JSESSIONID") //NEW Cookies to clear
                            .logoutSuccessHandler(customLogoutSuccessHandler)
                            .clearAuthentication(true)
                            .invalidateHttpSession(true))
                    .addFilterAfter(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            ;
            return http.build();
        }


    }


//    @Autowired
//    private UserDetailsService uds;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11, new SecureRandom());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(uds);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
        return new AuthenticationProviderImpl(usuarioRepository, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        //your AuthenticationProvider must return UserDetails object
        AuthenticationProvider ap = authenticationProvider();
        log.info("########## JEEM: Se recibe el AuthenticationProvider:");
        log.info("{}",ap);
        AuthenticationManager am = new ProviderManager(ap);
        log.info("########## JEEM: Se crea bean AuthenticationManager:");
        log.info("{}",am);
        return am;
    }
}

