package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
	void deve_registrar_usuario_com_sucesso_pelo_service() {
		
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
	void deve_realizar_login_e_retornar_token_pelo_service() {
		AuthenticationDto auth = testUtil.getAuthenticationDto();
		
		LoginResponseDto loginResponse = usuarioAuthService.login(auth);
		
		assertNotNull(loginResponse.token());
		assertEquals(UserRole.ADMIN, loginResponse.role());
	}

}
