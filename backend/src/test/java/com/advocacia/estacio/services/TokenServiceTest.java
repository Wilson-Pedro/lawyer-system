package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TokenServiceTest {
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TestUtil testUtil;
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_DepoisDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve gerar Token")
	void gerar_token() {
		usuarioAuthRepository.save(testUtil.getUsuarioAuth());
		
		RegistroDto dto = testUtil.getRegistroDto();
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		
		var auth = authenticationManager.authenticate(usernamePassword);
		
		String token = tokenService.generateToken((UsuarioAuth) auth.getPrincipal());
		TOKEN = token;
		assertNotNull(token);
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve validar Token")
	void validar_token() {
		String login = tokenService.validateToken(TOKEN);
		
		UsuarioAuth userAuth = (UsuarioAuth) usuarioAuthRepository.findByLogin(login);
		
		assertEquals(userAuth.getLogin(), testUtil.getRegistroDto().login());
	}

}
