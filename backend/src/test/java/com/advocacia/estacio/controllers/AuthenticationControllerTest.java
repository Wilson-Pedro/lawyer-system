package com.advocacia.estacio.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.RegisterDto;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/auth";
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	void deveSalvar_registrar_usuario_PeloController() throws Exception {
		
		RegisterDto registerDto = testUtil.getRegisterDto();
		
		assertEquals(0, usuarioAuthRepository.count());
		
		String jsonRequest = objectMapper.writeValueAsString(registerDto);
		
		mockMvc.perform(post(URI + "/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
		
		String login = registerDto.login();
		UsuarioAuth userAuth = (UsuarioAuth) usuarioAuthRepository.findByLogin(login);
		
		assertTrue(registerDto.password() != userAuth.getPassword());
		assertEquals(1, usuarioAuthRepository.count());
	}
	
	
	@Test
	@Order(3)
	void deve_realizer_login_PeloController() throws Exception {
				
		AuthenticationDto authenticationDto = testUtil.getAuthenticationDto();
		
		String jsonRequest = objectMapper.writeValueAsString(authenticationDto);
		
		mockMvc.perform(post(URI + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
	}

}
