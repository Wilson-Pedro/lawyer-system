package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistidoControllerTest {
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/assistidos";
	
	AssistidoDto assistidoDto;
	
	@BeforeEach
	void setUp() {
		assistidoDto = testUtil.getAssistidoDto();
	}
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Assistido_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, assistidoRepository.count());
		
		String jsonRequest = objectMapper.writeValueAsString(assistidoDto);
		
		mockMvc.perform(post(URI + "/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Ana Carla")))
				.andExpect(jsonPath("$.matricula", equalTo("20250815")))
				.andExpect(jsonPath("$.telefone", equalTo("86766523354")))
				.andExpect(jsonPath("$.email", equalTo("ana@gmail.com")))
				.andExpect(jsonPath("$.profissao", equalTo("Cientista de Dados")))
				.andExpect(jsonPath("$.nacionalidade", equalTo("brasileiro")))
				.andExpect(jsonPath("$.naturalidade", equalTo("São Luís/MA")))
				.andExpect(jsonPath("$.estadoCivil", equalTo("Solteiro(a)")))
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila Palmeira")))
				.andExpect(jsonPath("$.rua", equalTo("rua dos nobres")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(12)))
				.andExpect(jsonPath("$.cep", equalTo("43012-232")));
		
		assertEquals(1, assistidoRepository.count());
	}
	
	@Test
	@Order(3)
	void deveSalvar_buscar_assistido_PeloController() throws Exception {
		
		String nome = "Car";
		
		mockMvc.perform(get(URI + "/buscar/" + nome)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.length()").value(1))
		.andExpect(jsonPath("$.content[0].nome").value("Ana Carla"));
		
	}
}
