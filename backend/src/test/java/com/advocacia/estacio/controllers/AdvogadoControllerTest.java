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

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdvogadoControllerTest {
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	AdvogadoDto advogadoDto;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/advogados";
	
	@BeforeEach
	void setUp() {
		advogadoDto = new AdvogadoDto(null, "Júlio Silva", "julio@gmail.com", "61946620131",
				"88566519808", "25/09/1996", "São Luís", "Vila Palmeira", 
				"rua dos nobres", 12, "43012-232");
	}
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_AntesDostestes() {
		demandaRepository.deleteAll();
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Advogado_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, advogadoRepository.count());
		
		String jsonRequest = objectMapper.writeValueAsString(advogadoDto);
		
		mockMvc.perform(post(URI + "/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Júlio Silva")))
				.andExpect(jsonPath("$.email", equalTo("julio@gmail.com")))
				.andExpect(jsonPath("$.cpf", equalTo("61946620131")))
				.andExpect(jsonPath("$.telefone", equalTo("88566519808")))
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila Palmeira")))
				.andExpect(jsonPath("$.rua", equalTo("rua dos nobres")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(12)))
				.andExpect(jsonPath("$.cep", equalTo("43012-232")));
		
		assertEquals(1, advogadoRepository.count());
	}
	
	@Test
	@Order(3)
	void deveBuscar_Advogado_peloNome_PeloController() throws Exception {
		
		String nome = "li";
		
		mockMvc.perform(get(URI + "/buscar/" + nome)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].nome").value("Júlio Silva"));
		
		assertEquals(1, advogadoRepository.count());
	}
}
