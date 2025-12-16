package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.advocacia.estacio.domain.enums.Tempestividade;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaServiceTest {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;

	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	DemandaService demandaService;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda No Banco de Dados Pelo Service")
	void salvar_demanda() {
		
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), "Corrigido", "02/11/2025", 10, "Dentro do Prazo");
		Demanda demanda = demandaService.salvar(demandaDto);
		
		assertNotNull(demanda);
		assertNotNull(demanda.getId());
		assertNotNull(demanda.getRegistro());
		assertEquals("Atualizar Documentos", demanda.getDemanda());
		assertEquals(demanda.getEstagiario(), estagiario);
		assertEquals("2025-11-12", demanda.getPrazo().toString());
		assertEquals("2025-11-02", demanda.getPrazoDocumentos().toString());
		assertEquals(Tempestividade.DENTRO_DO_PRAZO, demanda.getTempestividade());

		assertEquals(1, demandaRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Service")
	void buscar_demanda_por_status() {
		
		Page<DemandaDto> demandas = demandaService.buscarTodos(0, 20);
		
		assertNotNull(demandas);
		assertEquals("Atualizar Documentos", demandas.getContent().get(0).getDemanda());
		assertEquals("Pedro Lucas", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("12/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
	}
	
	@Test
	@Order(4)
	@DisplayName("Deve Buscar Demandas Pelo Estário Id No Banco de Dados Pelo Service")
	void buscar_demandas_por_estagiarioId() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		Page<DemandaDto> demandas = demandaService.buscarTodosPorEstagiarioId(estagiarioId, 0, 20);
		
		assertNotNull(demandas);
		assertEquals("Atualizar Documentos", demandas.getContent().get(0).getDemanda());
		assertEquals("Pedro Lucas", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("12/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Service")
	void deve_buscar_Demandas_por_status_NoBancoDeDados_PeloService() {
		Long estagiarioId2 = estagiarioService.salvar(testUtil.getEstagiarioDto2()).getId();
		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, "Em Correção", "02/11/2025", 13, "Dentro do Prazo");
		demandaService.salvar(demandaDto2);

		Page<DemandaDto> demandas = demandaService.buscarTodosPorStatus("Em Correção", 0, 20);

		assertNotNull(demandas);
		assertEquals("Organizar Processos", demandas.getContent().get(0).getDemanda());
		assertEquals("Carlos Miguel", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("15/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
	}
}
