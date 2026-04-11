package com.advocacia.estacio.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.repositories.DesativarAtivarUsuarioPorDataRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.EstagiarioService;
import com.advocacia.estacio.services.impl.UsuarioAuthServiceImpl;
import com.advocacia.estacio.utils.TestUtil;
import com.advocacia.estacio.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
	
	@Autowired
    UsuarioAuthServiceImpl usuarioAuthServiceImpl;

	@Autowired
	DesativarAtivarUsuarioPorDataRepository dataRepository;
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/auth";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_AntesDostestes() {

		testUtil.deleteAll();
		
		dataRepository.save(testUtil.getDataAtivacaoDto());
		dataRepository.save(testUtil.getDataDesativacaoDto());
		
		estagiarioService.salvar(testUtil.getEstagiarioDto2());
		estagiarioService.salvar(testUtil.getEstagiarioDto());
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Realizar Login Pelo Controller")
	void login() throws Exception {
		
		usuarioAuthServiceImpl.salvar(testUtil.getRegistroDtos().get(1));
				
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

		String hoje = Utils.localDateToString(LocalDate.now());
		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		data.setDataDeDesativacao(null);
		
		dataRepository.save(data);
		
		data = dataRepository.findAll().get(0);
		
		assertNull(data.getDataDeDesativacao());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, UsuarioStatus.ATIVO);

		String jsonRequest = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put(URI + "/definir/data/ativarDesativar")
						.header("Authorization", "Bearer " + TOKEN)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		data = dataRepository.findAll().get(0);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}

	@Test
	@DisplayName("Deve definir data para desativar usuários Pelo Controller")
	void deve_definir_data_para_desativar_usuarios() throws Exception {

		String hoje = Utils.localDateToString(LocalDate.now());
		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(1);
		assertNull(data.getDataDeDesativacao());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, UsuarioStatus.INATIVO);

		String jsonRequest = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put(URI + "/definir/data/ativarDesativar")
						.header("Authorization", "Bearer " + TOKEN)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		data = dataRepository.findAll().get(1);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}
	
	@Test
	@DisplayName("Deve ativar usuários por data Pelo Controller")
	void deve_ativar_usuario_por_data_usuarios() throws Exception {

		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		DesativarAtivarUsuarioPorData data2 = dataRepository.findAll().get(1);
		
		data.setDataDeDesativacao(LocalDate.now());
		data2.setDataDeDesativacao(null);
		dataRepository.save(data);
		dataRepository.save(data2);
		
		Estagiario estagiario = estagiarioRepository.findAll().get(0);
		Estagiario estagiario2 = estagiarioRepository.findAll().get(1);
		estagiario.getUsuarioAuth().setUsuarioStatus(UsuarioStatus.INATIVO);
		estagiario2.getUsuarioAuth().setUsuarioStatus(UsuarioStatus.INATIVO);
		estagiarioRepository.save(estagiario);
		estagiarioRepository.save(estagiario2);

		mockMvc.perform(post(URI + "/ativarDesativar/data")
						.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isNoContent());

		estagiario = estagiarioRepository.findAll().get(0);
		estagiario2 = estagiarioRepository.findAll().get(1);
		
		assertEquals(UsuarioStatus.ATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, estagiario2.getUsuarioAuth().getUsuarioStatus());
	}
	
	@Test
	@DisplayName("Deve desativar usuários por data Pelo Controller")
	void deve_desativar_usuario_por_data_usuarios() throws Exception {

		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		DesativarAtivarUsuarioPorData data2 = dataRepository.findAll().get(1);
		
		data.setDataDeDesativacao(null);
		data2.setDataDeDesativacao(LocalDate.now());
		dataRepository.save(data);
		dataRepository.save(data2);
		
		Estagiario estagiario = estagiarioRepository.findAll().get(0);
		Estagiario estagiario2 = estagiarioRepository.findAll().get(1);
		estagiario.getUsuarioAuth().setUsuarioStatus(UsuarioStatus.ATIVO);
		estagiario2.getUsuarioAuth().setUsuarioStatus(UsuarioStatus.ATIVO);
		estagiarioRepository.save(estagiario);
		estagiarioRepository.save(estagiario2);

		mockMvc.perform(post(URI + "/ativarDesativar/data")
						.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isNoContent());

		estagiario = estagiarioRepository.findAll().get(0);
		estagiario2 = estagiarioRepository.findAll().get(1);
		
		assertEquals(UsuarioStatus.INATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
		assertEquals(UsuarioStatus.INATIVO, estagiario2.getUsuarioAuth().getUsuarioStatus());
	}
}
