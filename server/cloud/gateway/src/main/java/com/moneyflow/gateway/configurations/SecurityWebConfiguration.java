package com.moneyflow.gateway.configurations;

import com.moneyflow.gateway.repository.UsuarioRepository;
import com.moneyflow.gateway.service.JwtAuthenticationFilter;
import com.moneyflow.gateway.service.JwtProperties;
import com.moneyflow.gateway.service.JwtTokenProvider;
import com.moneyflow.gateway.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityWebConfiguration {

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService (UsuarioRepository usuarioRepository) {
        return new UsuarioDetailsService(usuarioRepository);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity http, JwtTokenProvider jwtTokenProvider) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(new WebSessionServerSecurityContextRepository())
                .authorizeExchange(it -> it
                        .pathMatchers("/auth/**").permitAll()
                        .anyExchange().authenticated()
                ).addFilterAt(new JwtAuthenticationFilter(jwtTokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public JwtProperties jwtProperties () {
        return new JwtProperties();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager (ReactiveUserDetailsService reactiveUserDetailsService) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}
