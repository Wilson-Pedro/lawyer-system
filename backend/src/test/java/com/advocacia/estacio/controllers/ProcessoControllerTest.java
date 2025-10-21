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

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoControllerTest {	
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String URI = "/processos";
	
	Estagiario estagiario;
	
	AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
			"ana@gmail.com", "Cientista de Dados", "brasileiro", "São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	
	AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "julio@gmail.com", "61946620131",
			"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
			"rua do passeio", 11, "53022-112");
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Processo_NoBancoDeDados_PeloController() throws Exception {
		
		assertEquals(0, processoRepository.count());
		
		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
		Long advogadoId = advogadoService.salvar(advogadoDto).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "Seguro de Carro", "23423ee23", "Júlio", advogadoId, "25/10/2025");
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(post(URI + "/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.assistidoId").value(assistidoId))
				.andExpect(jsonPath("$.assunto", equalTo("Seguro de Carro")))
				.andExpect(jsonPath("$.vara", equalTo("23423ee23")))
				.andExpect(jsonPath("$.responsavel", equalTo("Júlio")))
				.andExpect(jsonPath("$.prazoFinal".toString(), equalTo("25/10/2025")))
				.andExpect(jsonPath("$.advogadoId".toString(), equalTo(advogadoId.intValue())));
		
		assertEquals(1, processoRepository.count());
	}
	
	@Test
	@Order(3)
	void deveBuscar_Processos_PorStatusDoProcesso_PeloController() throws Exception {
		
		mockMvc.perform(get(URI + "/statusDoProcesso")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].id").exists())
				.andExpect(jsonPath("$[0].numeroDoProcesso").exists())
				.andExpect(jsonPath("$[0].assunto").exists())
				.andExpect(jsonPath("$[0].prazoFinal").exists())
				.andExpect(jsonPath("$[0].responsavel").exists());
	}
	
	@Test
	@Order(4)
	void deveBuscar_Processo_pelo_NumeroDoProcesso_PeloController() throws Exception {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso();
		
		mockMvc.perform(get(URI + "/buscar/" + numeroDoProcesso)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].assunto").value("Seguro de Carro"));
	}
}
