package com.moneyflow.gateway.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

	private static final String AUTHORITIES_KEY = "roles";

	private final JwtProperties jwtProperties;

	private SecretKey secretKey;

	public JwtTokenProvider (JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	@PostConstruct
	protected void init() {
		var secret = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
		secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String createToken(Authentication authentication) {
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getValidityInMs()))
				.compact();
	}

	public Authentication getAuthentication(String token) {

		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
				.parseClaimsJws(token)
				.getBody();

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get(AUTHORITIES_KEY).toString());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	public boolean validateToken(String token) {

		try {

			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
					.parseClaimsJws(token);

			boolean isExpired = claims.getBody().getExpiration().before(new Date());

			return !isExpired;

		} catch (JwtException | IllegalArgumentException e) {
			log.debug("Invalid JWT token trace.", e);
		}

		return false;
	}


}
