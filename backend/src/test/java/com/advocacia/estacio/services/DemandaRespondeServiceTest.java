package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.RespondidoPor;
import com.advocacia.estacio.repositories.DemandaRespondeRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaRespondeServiceTest {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	DemandaRespondeRepository demandaRespondeRepository;

	@Autowired
	DemandaRespondeService demandaRespondeService;
	
	@Autowired
	DemandaService demandaService;

	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda Resposta No Banco de Dados Pelo Service")
	void salvar_demanda_resposta() {
		
		assertEquals(0, demandaRespondeRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), advogadoId, "Corrigido", "02/11/2025", 10, "Dentro do Prazo");
		Demanda demanda = demandaService.salvar(demandaDto);

		DemandaRespondeDto demandaRespondeDto = new DemandaRespondeDto(null, demanda.getId(), estagiario.getId(), "Documentação completa", "Estagiário");
		DemandaResponde demandaResponde = demandaRespondeService.salvar(demandaRespondeDto);

		assertNotNull(demandaResponde);
		assertNotNull(demandaResponde.getId());
		assertEquals(demandaRespondeDto.getResposta(), demandaResponde.getResposta());
		assertEquals(estagiario.getId(), demandaResponde.getEstagiario().getId());
		assertEquals(demanda.getId(), demandaResponde.getDemanda().getId());
		assertEquals(RespondidoPor.ESTAGIARIO, demandaResponde.getRespondidoPor());

		assertEquals(1, demandaRespondeRepository.count());
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Id No Banco de Dados Pelo Service")
	void buscar_demanda_por_demandaId() {

		Long demandaId = demandaRespondeRepository.findAll().get(0).getDemanda().getId();
		Page<DemandaRespondeDto> pages = demandaRespondeService.buscarDemandasRespostasPorDemandaId(demandaId, 0, 20);

		assertNotNull(pages);
		assertEquals(1, pages.getContent().size());
	}
}
