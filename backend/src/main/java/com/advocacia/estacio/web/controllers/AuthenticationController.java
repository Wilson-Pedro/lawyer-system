package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManger;
	
	@Autowired
	private UsuarioAuthRepository repository;
	
	@Autowired
	TokenService tokenService;
	
//	@PostMapping("/register")
//	public ResponseEntity register(@RequestBody RegisterDto dto) {
//		if(this.repository.findByLogin(dto.login()) != null) return ResponseEntity.badRequest().build();
//		
//		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
//		UsuarioAuth user = new UsuarioAuth(dto.login(), encryptedPassword, dto.role());
//		this.repository.save(user);
//		return ResponseEntity.ok().build();		
//	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationDto dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManger.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((UsuarioAuth) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDto(token));
	}
}
