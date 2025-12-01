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
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody AuthenticationDto dto) {
		return ResponseEntity.ok(usuarioAuthService.login(dto));
	}
}
