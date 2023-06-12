package com.moneyflow.gateway.configurations;

import com.moneyflow.gateway.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	@SneakyThrows
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
		httpSecurity.csrf().disable();
		httpSecurity.authorizeExchange(this::configureRequestMatchers);
		return httpSecurity.build();
	}

	@Bean
	public ReactiveAuthenticationManager authenticationProvider(ReactiveUserDetailsService userDetailsService) {
		final UserDetailsRepositoryReactiveAuthenticationManager authenticationProvider = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
		authenticationProvider.setPasswordEncoder(password());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder password() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public ReactiveUserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
		return new UserDetailsRepositoy(usuarioRepository);
	}

	@SneakyThrows
	private void configureRequestMatchers(AuthorizeHttpRequestsConfigurer<ServerHttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests) {
		authorizeRequests
				.pathMatchers("/api/auth/**").permitAll()
				.anyExchange().authenticated()
				.and()
				.httpBasic()
				.and()
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}
}