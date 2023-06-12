package com.moneyflow.gateway.service;

import com.moneyflow.gateway.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UsuarioDetailsService implements ReactiveUserDetailsService {

	private final UsuarioRepository userRepository;

	public UsuarioDetailsService(UsuarioRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername (String username) {
		return userRepository.findByEmail(username).map(usuario -> User.withUsername(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(usuario.getRoles().stream().map(role -> role.getRole().getRole()).toArray(String[]::new))
				.accountExpired(!usuario.isAccountNonExpired())
				.accountLocked(!usuario.isAccountNonLocked())
				.credentialsExpired(!usuario.isCredentialsNonExpired())
				.disabled(!usuario.isEnabled())
				.build());
	}

}
