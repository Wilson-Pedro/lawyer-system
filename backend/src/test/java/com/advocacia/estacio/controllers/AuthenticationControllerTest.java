package com.advocacia.estacio.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.services.impl.UsuarioAuthService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
	
	@Autowired
	UsuarioAuthService usuarioAuthService;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/auth";
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Realizar Login Pelo Controller")
	void login() throws Exception {
		
		usuarioAuthService.salvar(testUtil.getRegistroDto());
				
		AuthenticationDto authenticationDto = testUtil.getAuthenticationDto();
		
		String jsonRequest = objectMapper.writeValueAsString(authenticationDto);
		
		mockMvc.perform(post(URI + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Deve Buscar Usuario Status Pelo Controller")
	void buscar_usuario_stataus() throws Exception {

		String TOKEN = usuarioAuthService.login(testUtil.getAuthenticationDto()).token();

		mockMvc.perform(get(URI + "/usuarioStatus")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0]", CoreMatchers.equalTo("Ativo")))
				.andExpect(jsonPath("$[1]", CoreMatchers.equalTo("Inativo")));
	}
}
