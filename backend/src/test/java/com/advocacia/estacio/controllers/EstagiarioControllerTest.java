package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioControllerTest {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/estagiarios";
	
	private static String TOKEN = "";
	
	Estagiario estagiario;
	
//	@BeforeEach
//	void setUp() {
//		estagiario = testUtil.getEstagiario();
//	}
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Estagiario_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, estagiarioRepository.count());
		
		EstagiarioDto dto = new EstagiarioDto(testUtil.getEstagiario());
		
		String jsonRequest = objectMapper.writeValueAsString(dto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Pedro Lucas")))
				.andExpect(jsonPath("$.email", equalTo("pedro@gmail.com")))
				.andExpect(jsonPath("$.matricula", equalTo("20251208")))
				.andExpect(jsonPath("$.periodo", equalTo("Estágio I")));
		
		assertEquals(1, estagiarioRepository.count());
	}
	
	@Test
	@Order(3)
	void deveSalvar_buscar_estagiario_PeloController() throws Exception {
		var estagiario = estagiarioRepository.findAll().get(0);
		String nome = estagiario.getNome();
		
		mockMvc.perform(get(URI + "/buscar/" + nome)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("$.content[0].nome").value("Pedro Lucas"));
		
	}
}
