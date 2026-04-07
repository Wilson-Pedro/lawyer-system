package com.advocacia.estacio.web.controllers;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.services.UsuarioAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.services.impl.UsuarioAuthServiceImpl;

import java.util.List;

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

	@GetMapping("/usuarioStatus")
	public ResponseEntity<List<String>> buscarUsuarioStatus() {
		List<String> usuarioStatus = usuarioAuthService.getUsuarioStatus().stream().map(UsuarioStatus::getDescricao).toList();
		return ResponseEntity.ok(usuarioStatus);
	}

	@PutMapping("/data/ativarDesativar")
	public ResponseEntity<Void> definirDataParaAtivarDesativar(
			@RequestBody DesativarAtivarUsuarioPorDataDto dto
	) {
		this.usuarioAuthService.definirDataDeDesativacao(dto);
		return  ResponseEntity.noContent().build();
	}
}
