package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
	void deveSalvar_Demanda_NoBancoDeDadosPeloService() {
		
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), "Atendido", "12/11/2025");
		Demanda demanda = demandaService.salvar(demandaDto);
		
		assertNotNull(demanda);
		assertNotNull(demanda.getId());
		assertNotNull(demanda.getRegistro());
		assertEquals(demanda.getDemanda(), "Atualizar Documentos");
		assertEquals(demanda.getEstagiario(), estagiario);
		assertEquals(demanda.getPrazo().toString(), "2025-11-12");

		assertEquals(1, demandaRepository.count());
	}
	
	@Test
	@Order(3)
	void deve_buscar_Demandas_NoBancoDeDados_PeloService() {
		
		Page<DemandaDto> demandas = demandaService.buscarTodos(0, 20);
		
		assertNotNull(demandas);
		assertEquals(demandas.getContent().get(0).getDemanda(), "Atualizar Documentos");
		assertEquals(demandas.getContent().get(0).getEstagiarioNome(), "Pedro Lucas");
		assertEquals(demandas.getContent().get(0).getPrazo(), "2025-11-12");
	}
	
	@Test
	@Order(4)
	void deve_buscar_Demandas_por_estagiarioId_NoBancoDeDados_PeloService() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		Page<DemandaDto> demandas = demandaService.buscarTodosPorEstagiarioId(estagiarioId, 0, 20);
		
		assertNotNull(demandas);
		assertEquals(demandas.getContent().get(0).getDemanda(), "Atualizar Documentos");
		assertEquals(demandas.getContent().get(0).getEstagiarioNome(), "Pedro Lucas");
		assertEquals(demandas.getContent().get(0).getPrazo(), "2025-11-12");
	}

	@Test
	void deve_buscar_Demandas_por_status_NoBancoDeDados_PeloService() {
		Long estagiarioId2 = estagiarioService.salvar(testUtil.getEstagiarioDto2()).getId();
		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, "Não Atendido", "15/12/2025");
		demandaService.salvar(demandaDto2);

		Page<DemandaDto> demandas = demandaService.buscarTodosPorStatus("Não Atendido", 0, 20);

		assertNotNull(demandas);
		assertEquals(demandas.getContent().get(0).getDemanda(), "Organizar Processos");
		assertEquals(demandas.getContent().get(0).getEstagiarioNome(), "Carlos Miguel");
		assertEquals(demandas.getContent().get(0).getPrazo(), "2025-12-15");
	}
}
