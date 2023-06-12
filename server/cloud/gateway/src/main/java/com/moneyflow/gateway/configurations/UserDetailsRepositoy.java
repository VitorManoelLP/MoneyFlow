package com.moneyflow.gateway.configurations;

import com.moneyflow.gateway.domain.UsuarioRole;
import com.moneyflow.gateway.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class UserDetailsRepositoy implements ReactiveUserDetailsService {

	private final UsuarioRepository userRepository;

	public UserDetailsRepositoy(UsuarioRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userRepository.findByEmail(username)
				.map(usuario -> User.withUsername(usuario.getEmail())
						.password(usuario.getPassword())
						.roles(String.valueOf(usuario.getRoles().stream().map(UsuarioRole::getRole).collect(Collectors.toList())))
						.build())
				.switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuário não encontrado")));
	}

}
