package com.advocacia.estacio.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.repositories.DesativarAtivarUsuarioPorDataRepository;
import com.advocacia.estacio.utils.Utils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.services.impl.UsuarioAuthServiceImpl;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
	
	@Autowired
    UsuarioAuthServiceImpl usuarioAuthServiceImpl;

	@Autowired
	DesativarAtivarUsuarioPorDataRepository dataRepository;
	
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
		
		usuarioAuthServiceImpl.salvar(testUtil.getRegistroDtos().get(0));
				
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

		String TOKEN = usuarioAuthServiceImpl.login(testUtil.getAuthenticationDto()).token();

		mockMvc.perform(get(URI + "/usuarioStatus")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]", CoreMatchers.equalTo("Ativo")))
				.andExpect(jsonPath("$[1]", CoreMatchers.equalTo("Inativo")))
				.andExpect(jsonPath("$[2]", CoreMatchers.equalTo("Desligado")));
	}

	@Test
	@DisplayName("Deve definir data para ativar usuários Pelo Controller")
	void deve_definir_data_para_ativar_usuarios() throws Exception {

		String hoje = Utils.stringToLocalDate(LocalDate.now());
		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(1);
		assertNull(data.getDataDeDesativacao());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, UsuarioStatus.ATIVO);

		String jsonRequest = objectMapper.writeValueAsString(dto);

		String TOKEN = usuarioAuthServiceImpl.login(testUtil.getAuthenticationDto()).token();

		mockMvc.perform(put(URI + "/data/ativarDesativar")
						.header("Authorization", "Bearer " + TOKEN)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		data = dataRepository.findAll().get(1);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}

	@Test
	@DisplayName("Deve definir data para desativar usuários Pelo Controller")
	void deve_definir_data_para_desativar_usuarios() throws Exception {

		String hoje = Utils.stringToLocalDate(LocalDate.now());
		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		assertNull(data.getDataDeDesativacao());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, UsuarioStatus.INATIVO);

		String jsonRequest = objectMapper.writeValueAsString(dto);

		String TOKEN = usuarioAuthServiceImpl.login(testUtil.getAuthenticationDto()).token();

		mockMvc.perform(put(URI + "/data/ativarDesativar")
						.header("Authorization", "Bearer " + TOKEN)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		data = dataRepository.findAll().get(0);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}
}
