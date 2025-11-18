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

import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.MovimentoRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.MovimentoService;
import com.advocacia.estacio.services.ProcessoService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimentoControllerTest {
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	MovimentoService movimentoService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/movimentos";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Processo_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, movimentoRepository.count());
		
		Long assistidoId = assistidoService.salvar(testUtil.getAssistidoDto()).getId();
		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		Long estagiarioId = estagiarioRepository.save(testUtil.getEstagiario()).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId, estagiarioId, "Civil", "Estadual", "25/10/2025");
		
		Long processoId = processoService.salvar(request).getId();
		
		MovimentoDto movimentoDto = new MovimentoDto(null, processoId, advogadoId, "Seguro de Carro");
		
		String jsonRequest = objectMapper.writeValueAsString(movimentoDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.processoId").value(processoId.intValue()))
				.andExpect(jsonPath("$.advogadoId", equalTo(advogadoId.intValue())))
				.andExpect(jsonPath("$.movimento", equalTo(movimentoDto.getMovimento())));
		
		assertEquals(1, movimentoRepository.count());
	}
	
	@Test
	@Order(3)
	void deveBuscar_Processo_pelo_numeroDoProcesso_PeloController() throws Exception {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso();
		
		mockMvc.perform(get(URI + "/buscar/" + numeroDoProcesso)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].numeroDoProcesso").value("20251"));
	}
}
