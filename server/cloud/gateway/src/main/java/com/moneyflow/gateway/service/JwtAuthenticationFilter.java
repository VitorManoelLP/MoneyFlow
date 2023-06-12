package com.moneyflow.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private static final String HEADER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter (final ServerWebExchange exchange, final WebFilterChain chain) {

        final String token = resolveToken(exchange.getRequest());

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

            final Authentication authentication = jwtTokenProvider.getAuthentication(token);

            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }

        return chain.filter(exchange);
    }

    private String resolveToken (ServerHttpRequest request) {

        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
