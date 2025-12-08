package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.services.impl.UsuarioAuthService;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioAuthServiceTest {
	
	@Autowired
	UsuarioAuthService usuarioAuthService;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_DepoisDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve registrar usuário pelo service")
	void registrar_usuario() {
		
		assertEquals(0, usuarioAuthRepository.count());
		
		RegistroDto registroDto = testUtil.getRegistroDto();
		
		usuarioAuthService.salvar(registroDto);
		
		assertEquals(1, usuarioAuthRepository.count());
		
		UsuarioAuth user = usuarioAuthRepository.findAll().get(0);
		
		assertEquals(user.getUsername(), registroDto.login());
		assertEquals(UserRole.ADMIN, user.getRole());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve realizar login e retornar token pelo Service")
	void login_e_retornar_token() {
		AuthenticationDto auth = testUtil.getAuthenticationDto();
		
		LoginResponseDto loginResponse = usuarioAuthService.login(auth);
		
		assertNotNull(loginResponse.token());
		assertEquals(UserRole.ADMIN, loginResponse.role());
	}
	
	@Test
	@DisplayName("Deve Atualizar Senha pelo Service")
	void atualizar_senha() {
		UsuarioAuth usuario = usuarioAuthRepository.findAll().get(0);
		String senha = usuario.getPassword();
		
		usuarioAuthService.atualizarLogin(usuario.getLogin(), usuario.getLogin(), "12345");
		
		String senhaNova = usuarioAuthRepository.findAll().get(0).getPassword();
		
		assertNotEquals(senha, senhaNova);
		
		usuarioAuthService.login(new AuthenticationDto(usuario.getLogin(), "12345"));
	}
	
	@Test
	@DisplayName("Deve Atualizar Email pelo Service")
	void atualizar_login() {
		UsuarioAuth usuario = usuarioAuthRepository.findAll().get(0);
		String loginAntigo = usuario.getLogin();
		
		usuarioAuthService.atualizarLogin(usuario.getLogin(), "professor_22@gmail.com", "");
		
		String loginNovo = usuarioAuthRepository.findAll().get(0).getLogin();
		
		assertNotEquals(loginAntigo, loginNovo);
		
		usuarioAuthService.login(new AuthenticationDto("professor_22@gmail.com", "1234"));
	}
	
	@Test
	@DisplayName("Não Deve Atualizar Login pelo Service")
	void nao_atualizar_login() {
		UsuarioAuth usuario = usuarioAuthRepository.findAll().get(0);
		
		usuarioAuthService.atualizarLogin(usuario.getLogin(), usuario.getLogin(), "");
		
		UsuarioAuth mesmoUsuario = usuarioAuthRepository.findAll().get(0);
		
		assertEquals(usuario.getLogin(), mesmoUsuario.getLogin());
		assertEquals(usuario.getPassword(), mesmoUsuario.getPassword());
	}
}
