package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;
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

	
	public UsuarioAuth salvar(RegistroDto dto) {
		if(this.usuarioAuthRepository.findByLogin(dto.login()) != null) {
			throw new RuntimeException("Usuário já cadastrado.");
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
		UsuarioAuth user = new UsuarioAuth(dto.login(), encryptedPassword, dto.role());
		user.setUsuarioStatus(UsuarioStatus.ATIVO);
		return this.usuarioAuthRepository.save(user);
	}

	public LoginResponseDto login(AuthenticationDto dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManger.authenticate(usernamePassword);
		
		UsuarioAuth user = (UsuarioAuth) usuarioAuthRepository.findByLogin(dto.login());
		String token = null;
		if(user.getUsuarioStatus().equals(UsuarioStatus.ATIVO)) {
			token =  tokenService.generateToken((UsuarioAuth) auth.getPrincipal());

			return new LoginResponseDto(token, dto.login(), user.getRole());
		}
		return new LoginResponseDto(token, null, null);
	}
	
	public void atualizarLogin(String loginAntigo, String loginNovo, String senha, UsuarioStatus usuarioStatus) {
		UsuarioAuth user = (UsuarioAuth) usuarioAuthRepository.findByLogin(loginAntigo);
		boolean atualizar = false;

		if(!user.getUsuarioStatus().equals(usuarioStatus)) {
			user.setUsuarioStatus(usuarioStatus);
			atualizar = true;
		}
		
		if(!loginAntigo.trim().equals(loginNovo.trim())) {
			user.setLogin(loginNovo);
			atualizar = true;
		}
		
		if(!senha.isEmpty() || !senha.isBlank()) {
			String encryptedPassword = new BCryptPasswordEncoder().encode(senha);
			user.setPassword(encryptedPassword);	
			atualizar = true;
		}

		if(atualizar) this.usuarioAuthRepository.save(user);
	}
}
