package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaControllerTest {
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	AdvogadoDto advogadoDto;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/demandas";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Demanda_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());
		
		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Processos", estagiario.getId(), "Atendido", "12/11/2025");
		
		String jsonRequest = objectMapper.writeValueAsString(demandaDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.demanda", equalTo("Atualizar Processos")))
				.andExpect(jsonPath("$.estagiarioNome", equalTo("Pedro Lucas")))
				.andExpect(jsonPath("$.estagiarioId", equalTo(estagiario.getId().intValue())))
				.andExpect(jsonPath("$.demandaStatus", equalTo("Atendido")))
				.andExpect(jsonPath("$.prazo", equalTo("12/11/2025")));
		
		assertEquals(1, demandaRepository.count());
	}
	
	@Test
	@Order(3)
	void deve_buscar_Demandas_NoBancoDeDados_PeloController() throws Exception {
		
		mockMvc.perform(get(URI)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].demanda").value("Atualizar Processos"));
	}
	
	@Test
	@Order(4)
	void deve_buscar_Demandas_por_estagiarioId_NoBancoDeDados_PeloController() throws Exception {
		
		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(URI + "/estagiario/" + estagiarioId)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].estagiarioNome").value("Pedro Lucas"));
	}
}
