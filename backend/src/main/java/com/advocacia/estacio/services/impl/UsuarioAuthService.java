package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.RegisterDto;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;

@Service
public class UsuarioAuthService  {
	
	@Autowired
	AuthenticationManager authenticationManger;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;	
	
	@Autowired
	TokenService tokenService;

	
	public void registrar(RegisterDto dto) {
		if(this.usuarioAuthRepository.findByLogin(dto.login()) != null) {
			throw new RuntimeException("Usuário já cadastrado.");
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
		UsuarioAuth user = new UsuarioAuth(dto.login(), encryptedPassword, dto.role());
		this.usuarioAuthRepository.save(user);
	}

	public String login(AuthenticationDto dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManger.authenticate(usernamePassword);
		
		return tokenService.generateToken((UsuarioAuth) auth.getPrincipal());
	}
}
