package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.EstagiarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioControllerTest {
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/estagiarios";
	
	Estagiario estagiario;
	
	@BeforeEach
	void setUp() {
		estagiario = new Estagiario(
				"Pedro Lucas", "pedro@gmail.com", "20251208", 
				PeriodoEstagio.ESTAGIO_I, "1234");
	}
	
	@Test
	@Order(1)
	void deveSalvar_Estagiario_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, estagiarioRepository.count());
		
		EstagiarioDto dto = new EstagiarioDto(estagiario);
		
		String jsonRequest = objectMapper.writeValueAsString(dto);
		
		mockMvc.perform(post(URI + "/")
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
	@Order(2)
	void deletandoTodosOsDadosAntesDostestes() {
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}
}
