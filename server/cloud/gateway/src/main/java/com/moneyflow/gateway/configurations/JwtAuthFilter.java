package com.moneyflow.gateway.configurations;

import com.moneyflow.gateway.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

	private final UsuarioRepository usuarioRepository;

	@Override
	public Mono<Void> filter (ServerWebExchange exchange, WebFilterChain chain) {

		final String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (isInvalidToken(authHeader)) {
			return chain.filter(exchange);
		}

		final String token = Objects.requireNonNull(authHeader).substring(7);
		final String username = JwtTokenValidator.getUsername(token);

		if (username != null && ReactiveSecurityContextHolder.getContext().getAuthentication() == null) {

			return usuarioRepository.findOneByEmail(username)
					.flatMap(userDetails -> {
						if (JwtTokenValidator.isValidToken(token, userDetails)) {

							final UsernamePasswordAuthenticationToken auth = getAuth(userDetails);

							return new WebFilterChainServerAuthenticationSuccessHandler()
									.onAuthenticationSuccess(new WebFilterExchange(exchange, chain), auth)
									.contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

						} else {
							return chain.filter(exchange);
						}
					});

		}

		return chain.filter(exchange);
	}

	private static UsernamePasswordAuthenticationToken getAuth(UserDetails userDetails) {
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	private boolean isInvalidToken(String authHeader) {
		return Objects.isNull(authHeader) || !authHeader.startsWith("Bearer");
	}
}