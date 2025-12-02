package com.advocacia.estacio.controllers;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.DemandaRespondeRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.DemandaRespondeService;
import com.advocacia.estacio.services.DemandaService;
import com.advocacia.estacio.services.EstagiarioService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaRespondeControllerTest {
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;

	@Autowired
	DemandaRespondeRepository demandaRespondeRepository;

	@Autowired
	DemandaRespondeService demandaRespondeService;

	@Autowired
	DemandaService demandaService;
	
	AdvogadoDto advogadoDto;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	private static String URI = "/demandas/responde";

	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	void deveSalvar_DemandaResponde_NoBancoDeDados_PeloController() throws Exception {

		assertEquals(0, demandaRespondeRepository.count());

		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), "Atendido", "12/11/2025");
		Demanda demanda = demandaService.salvar(demandaDto);

		DemandaRespondeDto demandaRespondeDto = new DemandaRespondeDto(null, demanda.getId(), estagiario.getId(), "Documentação completa", "Estagiário");

		String jsonRequest = objectMapper.writeValueAsString(demandaRespondeDto);

		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.resposta", equalTo(demandaRespondeDto.getResposta())))
				.andExpect(jsonPath("$.respondidoPor", equalTo(demandaRespondeDto.getRespondidoPor())));
		
		assertEquals(1, demandaRespondeRepository.count());
	}

	void deve_buscar_DemandaResponde_por_demandaId_PeloController() throws Exception {

		Long demandaId = demandaRepository.findAll().get(0).getId();

		mockMvc.perform(post(URI + "/demanda/" + demandaId)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()", equalTo(1)));
	}
}
