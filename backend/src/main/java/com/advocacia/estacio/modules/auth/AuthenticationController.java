package com.advocacia.estacio.modules.auth;

import com.advocacia.estacio.modules.usuarios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final UsuarioService usuarioService;

	public AuthenticationController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthDTO.LoginResponse> login(@RequestBody @Valid AuthDTO.LoginRequest dto) {
		return ResponseEntity.ok(usuarioService.login(dto));
	}

//	@GetMapping("/usuarioStatus")
//	public ResponseEntity<List<String>> buscarUsuarioStatus() {
//		List<String> usuarioStatus = usuarioService.getUsuarioStatus().stream().map(UsuarioStatus::getDescricao).toList();
//		return ResponseEntity.ok(usuarioStatus);
//	}
//
//	@PutMapping("/definir/data/ativarDesativar")
//	public ResponseEntity<Void> definirDataParaAtivarDesativar(
//			@RequestBody DesativarAtivarUsuarioPorDataDto dto
//	) {
//		this.usuarioService.definirDataDeDesativacao(dto);
//		return  ResponseEntity.noContent().build();
//	}
}
