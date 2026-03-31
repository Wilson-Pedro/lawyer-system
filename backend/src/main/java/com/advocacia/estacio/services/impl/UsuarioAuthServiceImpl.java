package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.repositories.DesativarAtivarUsuarioPorDataRepository;
import com.advocacia.estacio.services.UsuarioAuthService;
import com.advocacia.estacio.utils.Utils;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.advocacia.estacio.utils.Utils.stringToLocalDate;

@Service
public class UsuarioAuthServiceImpl implements UsuarioAuthService {
	
	@Autowired
	AuthenticationManager authenticationManger;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;

	@Autowired
	DesativarAtivarUsuarioPorDataRepository desativarAtivarUsuarioPorDataRepository;
	
	@Autowired
	TokenService tokenService;

	@Override
	public UsuarioAuth salvar(RegistroDto dto) {
		if(this.usuarioAuthRepository.findByLogin(dto.login()) != null) {
			throw new RuntimeException("Usuário já cadastrado.");
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
		UsuarioAuth user = new UsuarioAuth(dto.login(), encryptedPassword, dto.role());
		user.setUsuarioStatus(UsuarioStatus.ATIVO);
		return this.usuarioAuthRepository.save(user);
	}

	@Override
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

	@Override
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
		
		if(!senha.isEmpty()) {
			String encryptedPassword = new BCryptPasswordEncoder().encode(senha);
			user.setPassword(encryptedPassword);	
			atualizar = true;
		}

		if(atualizar) this.usuarioAuthRepository.save(user);
	}


	@Override
	public List<UsuarioStatus> getUsuarioStatus() {
		return Arrays.stream(UsuarioStatus.values()).toList();
	}

	@Override
	public void ativarUsuarios(List<UsuarioAuth> usuarioAuths) {
		desativarAtivarUsuarios(usuarioAuths, UsuarioStatus.ATIVO);
	}

	@Override
	public void desativarAtivarUsuarios(List<UsuarioAuth> usuarioAuths, UsuarioStatus usuarioStatus) {
		List<UsuarioAuth> list = usuarioAuths
				.stream()
				.map(user -> {
						user.setUsuarioStatus(usuarioStatus);
						return user;
				}).toList();
		usuarioAuthRepository.saveAll(list);
	}

	@Override
	public void desativarAtivarUsuariosPorData(LocalDate dataParaDesativar, List<UsuarioAuth> usuarioAuths, UsuarioStatus usuarioStatus) {
		LocalDate dataHoje = LocalDate.now();
		if(dataParaDesativar.isEqual(dataHoje)) desativarAtivarUsuarios(usuarioAuths, usuarioStatus);
	}

	@Override
	public void definirDataDeDesativacao(DesativarAtivarUsuarioPorDataDto dto) {
		DesativarAtivarUsuarioPorData data = desativarAtivarUsuarioPorDataRepository.findAll().stream()
				.filter(d ->
						d.getTipoUsuario().getRole().equals(dto.getTipoUsuario()) &&
						d.getUsuarioStatus().equals(dto.getUsuarioStatus()))
				.findFirst()
				.orElseThrow(null);
		if(data != null) {
			LocalDate  localDate = Utils.stringToLocalDate(dto.getDataDeDesativacao());
			data.setDataDeDesativacao(localDate);
			data.setId(data.getId());
			desativarAtivarUsuarioPorDataRepository.save(data);
		}
	}

	@Override
	public DesativarAtivarUsuarioPorData buscarDesativarUsuarioPorId(Long id) {
		return this.desativarAtivarUsuarioPorDataRepository.findById(id)
				.orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public void definirDataParaAtivarDesativar(Long id, String data) {
		DesativarAtivarUsuarioPorData desativarAtivarUsuarioPorData = buscarDesativarUsuarioPorId(id);
		LocalDate localDate = Utils.stringToLocalDate(data);
		desativarAtivarUsuarioPorData.setDataDeDesativacao(localDate);
		this.desativarAtivarUsuarioPorDataRepository.save(desativarAtivarUsuarioPorData);
	}

	@Override
	public List<UsuarioAuth> buscarUsuariosAuthPorRole(UserRole userRole) {
		return usuarioAuthRepository.findAll().stream()
				.filter(u -> u.getRole().equals(userRole))
				.toList();
	}
}
