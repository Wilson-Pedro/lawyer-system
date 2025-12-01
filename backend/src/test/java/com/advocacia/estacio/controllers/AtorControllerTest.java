package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.services.AtorService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AtorControllerTest {
	
	@Autowired
	AtorService atorService;
	
	@Autowired
	AtorRepository atorRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/atores";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Ator_Coordenador_NoBancoDeDados_Pelo_Controller() throws Exception {
		
		assertEquals(0, atorRepository.count());
		
		AtorDto atorDto = testUtil.getAtores().get(0);
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Roberto Carlos")))
				.andExpect(jsonPath("$.email", equalTo("roberto@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Coordenador do curso")));
		
		assertEquals(1, atorRepository.count());
	}
	
	@Test
	@Order(3)
	void deveSalvar_Ator_Secretario_NoBancoDeDados_Pelo_Controller() throws Exception {
		
		AtorDto atorDto = testUtil.getAtores().get(1);
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("José Augusto")))
				.andExpect(jsonPath("$.email", equalTo("jose@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Secretário")));
		
		assertEquals(2, atorRepository.count());
	}
	
	@Test
	@Order(4)
	void deveSalvar_Ator_Professor_NoBancoDeDados_Pelo_Controller() throws Exception {
		
		AtorDto atorDto = testUtil.getAtores().get(2);
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Fabio Junior")))
				.andExpect(jsonPath("$.email", equalTo("fabio@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Professor")));
		
		assertEquals(3, atorRepository.count());
	}
}
