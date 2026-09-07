package com.advocacia.estacio.modules.usuarios;

import com.advocacia.estacio.modules.auth.AuthDTO;
import com.advocacia.estacio.infra.exceptions.ConflitoDeDadosException;
import com.advocacia.estacio.infra.security.CustomUserDetails;
import com.advocacia.estacio.modules.usuarios.enums.UsuarioRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.infra.security.TokenService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final AuthenticationManager authenticationManager;
	private final UsuarioRepository usuarioRepository;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;

	public Usuario cadastrar(String login, String senha, UsuarioRole role) {
		if (this.usuarioRepository.findByLogin(login).isPresent()) {
			throw new ConflitoDeDadosException("Já existe um usuário cadastrado com este login.");
		}
		String encryptedPassword = passwordEncoder.encode(senha);
		Usuario user = new Usuario(login, encryptedPassword, role);
		return this.usuarioRepository.save(user);
	}


	public AuthDTO.LoginResponse login(AuthDTO.LoginRequest dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		Authentication auth = this.authenticationManager.authenticate(usernamePassword);

		CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
		String token = tokenService.generateToken(customUser);
		Instant expiracao = tokenService.getExpirationDate();

		return new AuthDTO.LoginResponse(
				token,
				"Bearer",
				customUser.getId(),
				customUser.getUsername(),
				customUser.getRole(),
				expiracao);
	}


	// TODO: servico auxilidar de criacao de senha
}