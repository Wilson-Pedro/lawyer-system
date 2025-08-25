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

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistidoControllerTest {
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/assistidos";
	
	AssistidoDto assistidoDto;
	
	@BeforeEach
	void setUp() {
		assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	}
	
	@Test
	@Order(1)
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
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila Palmeira")))
				.andExpect(jsonPath("$.rua", equalTo("rua dos nobres")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(12)))
				.andExpect(jsonPath("$.cep", equalTo("43012-232")));
		
		assertEquals(1, assistidoRepository.count());
	}

	@Test
	@Order(2)
	void deletandoTodosOsDadosAntesDostestes() {
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}
}
