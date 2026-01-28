package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioAuthServiceImplTest {
	
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
		
		RegistroDto registroDto = testUtil.getRegistroDtos().get(0);

		usuarioAuthService.salvar(registroDto);
		
		assertEquals(1, usuarioAuthRepository.count());
		
		UsuarioAuth user = usuarioAuthRepository.findAll().get(0);
		
		assertEquals(user.getUsername(), registroDto.login());
		assertEquals(UserRole.ADMIN, user.getRole());
		assertEquals(UsuarioStatus.ATIVO, user.getUsuarioStatus());
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
	@Order(4)
	@DisplayName("Deve Desativar todos os usuários por UsuárioStatus")
	void desativar_usuarios() {
		usuarioAuthService.salvar(testUtil.getRegistroDtos().get(1));
		usuarioAuthService.salvar(testUtil.getRegistroDtos().get(2));

		List<UsuarioAuth> usuariosAuth = usuarioAuthRepository.findAll();

		usuarioAuthService.desativarUsuarios(usuariosAuth);
		assertEquals(UsuarioStatus.INATIVO, usuariosAuth.get(0).getUsuarioStatus());
		assertEquals(UsuarioStatus.INATIVO, usuariosAuth.get(1).getUsuarioStatus());
		assertEquals(UsuarioStatus.INATIVO, usuariosAuth.get(2).getUsuarioStatus());
	}

	@Test
	@Order(5)
	@DisplayName("Deve Ativar todos os usuários por UsuárioStatus")
	void ativar_usuarios() {

		List<UsuarioAuth> usuariosAuth = usuarioAuthRepository.findAll();

		usuarioAuthService.ativarUsuarios(usuariosAuth);
		assertEquals(UsuarioStatus.ATIVO, usuariosAuth.get(0).getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, usuariosAuth.get(1).getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, usuariosAuth.get(2).getUsuarioStatus());
	}
	
	@Test
	@DisplayName("Deve Atualizar Senha pelo Service")
	void atualizar_senha() {
		UsuarioAuth usuario = usuarioAuthRepository.findAll().get(0);
		String senha = usuario.getPassword();

		usuarioAuthService.atualizarLogin(usuario.getLogin(), usuario.getLogin(), "12345", UsuarioStatus.ATIVO);
		
		String senhaNova = usuarioAuthRepository.findAll().get(0).getPassword();
		
		assertNotEquals(senha, senhaNova);

		usuarioAuthService.login(new AuthenticationDto(usuario.getLogin(), "12345"));
	}
	
	@Test
	@DisplayName("Deve Atualizar Email pelo Service")
	void atualizar_login() {
		UsuarioAuth usuario = usuarioAuthRepository.findAll().get(0);
		String loginAntigo = usuario.getLogin();

		usuarioAuthService.atualizarLogin(usuario.getLogin(), "professor_22@gmail.com", "", UsuarioStatus.ATIVO);
		
		String loginNovo = usuarioAuthRepository.findAll().get(0).getLogin();
		
		assertNotEquals(loginAntigo, loginNovo);

		usuarioAuthService.login(new AuthenticationDto("professor_22@gmail.com", "1234"));
	}
	
	@Test
	@DisplayName("Não Deve Atualizar Login pelo Service")
	void nao_atualizar_login() {
		UsuarioAuth usuario = usuarioAuthRepository.findAll().get(0);

		usuarioAuthService.atualizarLogin(usuario.getLogin(), usuario.getLogin(), "", UsuarioStatus.ATIVO);
		
		UsuarioAuth mesmoUsuario = usuarioAuthRepository.findAll().get(0);
		
		assertEquals(usuario.getLogin(), mesmoUsuario.getLogin());
		assertEquals(usuario.getPassword(), mesmoUsuario.getPassword());
	}

	@Test
	@DisplayName("Deve buscar Usuario Status Pelo Service")
	void buscar_usuario_status() {
		List<UsuarioStatus> usuarioStatus = usuarioAuthService.getUsuarioStatus();

		assertEquals(UsuarioStatus.ATIVO, usuarioStatus.get(0));
		assertEquals(UsuarioStatus.INATIVO, usuarioStatus.get(1));;
	}
}
