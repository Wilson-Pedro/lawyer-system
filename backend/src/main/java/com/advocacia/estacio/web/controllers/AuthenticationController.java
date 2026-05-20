package com.advocacia.estacio.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.services.UsuarioAuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${cors.allowed.origins}")
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

	@PutMapping("/definir/data/ativarDesativar")
	public ResponseEntity<Void> definirDataParaAtivarDesativar(
			@RequestBody DesativarAtivarUsuarioPorDataDto dto
	) {
		this.usuarioAuthService.definirDataDeDesativacao(dto);
		return  ResponseEntity.noContent().build();
	}
}
