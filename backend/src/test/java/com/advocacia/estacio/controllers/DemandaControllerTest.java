package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.DemandaService;
import com.advocacia.estacio.services.EstagiarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

	@Autowired
	EstagiarioService estagiarioService;

	@Autowired
	AdvogadoService advogadoService;

	@Autowired
	DemandaService demandaService;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/demandas";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda No Banco de Dados Pelo Controller")
	void salvar_demanda() throws Exception {
		
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		
		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Processos", estagiario.getId(), advogadoId, "Em Correção", "Aguardando Professor", "02/11/2025", 10, "Dentro do Prazo");
		
		String jsonRequest = objectMapper.writeValueAsString(demandaDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.demanda", equalTo("Atualizar Processos")))
				.andExpect(jsonPath("$.estagiarioNome", equalTo("Pedro Lucas")))
				.andExpect(jsonPath("$.estagiarioId", equalTo(estagiario.getId().intValue())))
				.andExpect(jsonPath("$.advogadoId", equalTo(advogadoId.intValue())))
				.andExpect(jsonPath("$.demandaStatusAluno", equalTo("Em Correção")))
				.andExpect(jsonPath("$.demandaStatusProfessor", equalTo("Aguardando Professor")))
				.andExpect(jsonPath("$.prazo", equalTo("12/11/2025")))
				.andExpect(jsonPath("$.tempestividade", equalTo("Dentro do Prazo")));
		
		assertEquals(1, demandaRepository.count());
	}

	@Test
	@Order(3)
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Controller")
	void buscar_demanda_por_status() throws Exception {

		Long estagiarioId2 = estagiarioService.salvar(testUtil.getEstagiarioDto2()).getId();

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto2()).getId();

		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, advogadoId, "Corrigido", "Aguardando Professor", "02/11/2025", 13, "Dentro do Prazo");
		demandaService.salvar(demandaDto2);

		mockMvc.perform(get(URI + "/status/Corrigido?page=0&size=20")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].demanda").value("Organizar Processos"))
				.andExpect(jsonPath("content[0].demandaStatusAluno").value("Corrigido"));
	}

    @Test
    @Order(4)
    @DisplayName("Deve Mudar Demanda Status Pelo Controller")
    void mudar_demanda_status() throws Exception {

        Long id = demandaRepository.findAll().get(0).getId();
        String status = "Devolvido";

        mockMvc.perform(patch(URI + "/" + id + "/change?status=" + status)
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isNoContent());

        Demanda demanda = demandaRepository.findById(id).get();

        assertEquals(DemandaStatus.DEVOLVIDO, demanda.getDemandaStatusAluno());
    }

	@Test
	@DisplayName("Deve Buscar Todas as Demanda Pelo Status No Banco de Dados Pelo Controller")
	void buscar_todas_as_demandas() throws Exception {

		mockMvc.perform(get(URI + "/status/todos?page=0&size=20")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(2));
	}
	
	@Test
	@DisplayName("Deve Buscar Demanda No Banco de Dados Pelo Controller")
	void buscar_demandas() throws Exception {
		
		mockMvc.perform(get(URI)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(2));
	}
	
	@Test
	@DisplayName("Deve Buscar Demanda Por Estagiario Id No Banco de Dados Pelo Controller")
	void buscar_demandas_por_estagiarioId() throws Exception {
		
		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(URI + "/estagiario/" + estagiarioId)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].estagiarioNome").value("Pedro Lucas"));
	}
}
