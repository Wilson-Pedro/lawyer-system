package com.advocacia.estacio.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(UsuarioAuth user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("lawyer-system")
					.withSubject(user.getLogin())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			
		} catch(JWTCreationException e) {
			throw new RuntimeException("Error ao gerar token " + e);
		}
	}
	
	public String validateToken(String token) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("lawyer-system")
					.build()
					.verify(token)
					.getSubject();
			
		} catch(JWTVerificationException e) {
			return "";
		}
	}
	
	private Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
