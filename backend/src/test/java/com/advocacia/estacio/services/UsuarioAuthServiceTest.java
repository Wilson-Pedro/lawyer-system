package com.advocacia.estacio.services;

import static com.advocacia.estacio.domain.enums.UsuarioStatus.INATIVO;
import static com.advocacia.estacio.domain.enums.UsuarioStatus.ATIVO;
import static com.advocacia.estacio.utils.Utils.localDateToString;
import static org.junit.jupiter.api.Assertions.*;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.repositories.DesativarAtivarUsuarioPorDataRepository;
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

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioAuthServiceTest {
	
	@Autowired
	UsuarioAuthService usuarioAuthService;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;

	@Autowired
	DesativarAtivarUsuarioPorDataRepository dataRepository;

	@Autowired
	EstagiarioService estagiarioService;
	
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

		usuarioAuthService.desativarAtivarUsuarios(usuariosAuth, INATIVO);
		assertEquals(INATIVO, usuariosAuth.get(0).getUsuarioStatus());
		assertEquals(INATIVO, usuariosAuth.get(1).getUsuarioStatus());
		assertEquals(INATIVO, usuariosAuth.get(2).getUsuarioStatus());
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
		assertEquals(INATIVO, usuarioStatus.get(1));;
	}

	@Test
	@DisplayName("Deve definir data de desativacao")
	void definir_data_de_desativacao() {
		String data = "25/10/2026";
		Long id = dataRepository.findAll().get(1).getId();
		DesativarAtivarUsuarioPorData desativarAtivarUsuarioPorData = this.usuarioAuthService.buscarDesativarUsuarioPorId(id);
		desativarAtivarUsuarioPorData.setDataDeDesativacao(null);
		dataRepository.save(desativarAtivarUsuarioPorData);
		assertNull(desativarAtivarUsuarioPorData.getDataDeDesativacao());

		this.usuarioAuthService.definirDataParaAtivarDesativar(1L, data);

		desativarAtivarUsuarioPorData = this.usuarioAuthService.buscarDesativarUsuarioPorId(1L);
		LocalDate localDate = localDateToString(data);
		assertEquals(localDate, desativarAtivarUsuarioPorData.getDataDeDesativacao());
	}

	@Test
	@DisplayName("Deve desativar usuários por data de desativacao")
	void desativar_usuarios_por_data_de_desativacao() {

		estagiarioService.salvar(testUtil.getEstagiarioDto2());
		LocalDate hoje = LocalDate.now();
		String hojeStr = localDateToString(hoje);

		DesativarAtivarUsuarioPorData desativarUsuario = new DesativarAtivarUsuarioPorData(UserRole.ESTAGIARIO, hoje, INATIVO);
		this.dataRepository.save(desativarUsuario);

		DesativarAtivarUsuarioPorDataDto desativarUsuarioDto = new DesativarAtivarUsuarioPorDataDto(
				desativarUsuario.getTipoUsuario().getRole(), hojeStr,desativarUsuario.getUsuarioStatus());

		List<UsuarioAuth> estagiarioAuth = usuarioAuthService.buscarUsuariosAuthPorRole(UserRole.ESTAGIARIO);
		assertEquals(ATIVO, estagiarioAuth.get(0).getUsuarioStatus());

		usuarioAuthService.desativarAtivarUsuariosPorData(desativarUsuarioDto);

		estagiarioAuth = usuarioAuthService.buscarUsuariosAuthPorRole(UserRole.ESTAGIARIO);
		assertEquals(INATIVO, estagiarioAuth.get(0).getUsuarioStatus());

	}

	@Test
	@DisplayName("Deve definir data para ativar usuários")
	void deve_definir_data_para_ativar_usuarios() {

		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		data.setDataDeDesativacao(null);
		data.setUsuarioStatus(ATIVO);
		dataRepository.save(data);

		data = dataRepository.findAll().get(0);

		assertNull(data.getDataDeDesativacao());

		String hoje = localDateToString(LocalDate.now());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, UsuarioStatus.ATIVO);

		this.usuarioAuthService.definirDataDeDesativacao(dto);

		data = dataRepository.findAll().get(1);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}

	@Test
	@DisplayName("Deve definir data para desativar usuários")
	void deve_definir_data_para_desativar_usuarios() {

		dataRepository.save(testUtil.getDataDesativacaoDto());

		String hoje = localDateToString(LocalDate.now());
		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		assertNull(data.getDataDeDesativacao());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, INATIVO);

		this.usuarioAuthService.definirDataDeDesativacao(dto);

		data = dataRepository.findAll().get(0);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}

	private List<UsuarioAuth> buscarUsuariosAuthPorRole(UserRole userRole) {
		return usuarioAuthRepository.findAll().stream()
				.filter(u -> u.getRole().equals(userRole))
				.toList();
	}
}
