package com.moneyflow.gateway.resource;

import com.moneyflow.gateway.dto.AuthenticationRequest;
import com.moneyflow.gateway.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

	private final JwtTokenProvider jwtTokenProvider;
	private final ReactiveAuthenticationManager reactiveAuthenticationManager;

	@PostMapping("/login")

	public Mono<ResponseEntity<Map<String, String>>> login (@Valid @RequestBody Mono<AuthenticationRequest> authRequest) {

		return authRequest
				.flatMap(login -> reactiveAuthenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
						.map(jwtTokenProvider::createToken)
				).map(this::authenticate);
	}

	private ResponseEntity<Map<String, String>> authenticate (String jwt) {
		HttpHeaders httpHeaders = getHttpHeaders(jwt);
		var tokenBody = Map.of("id_token", jwt);
		return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
	}

	private HttpHeaders getHttpHeaders (String jwt) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
		return httpHeaders;
	}

}
