package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.services.impl.UsuarioAuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {
	
	@Autowired
	UsuarioAuthService usuarioAuthService;
	
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
	public ResponseEntity<LoginResponseDto> login(@RequestBody AuthenticationDto dto) {
		String token = usuarioAuthService.login(dto);
		return ResponseEntity.ok(new LoginResponseDto(token));
	}
}
