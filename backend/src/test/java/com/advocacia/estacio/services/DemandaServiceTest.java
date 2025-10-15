package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaServiceTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	DemandaService demandaService;
	
	@Autowired
	TestUtil testUtil;
	
	Estagiario estagiario = new Estagiario(
			"Pedro Lucas", "pedro@gmail.com", "20251208", 
			PeriodoEstagio.ESTAGIO_I, "1234");
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	void deveSalvar_Demanda_NoBancoDeDadosPeloService() {
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(this.estagiario);
		
		DemandaDto demandaDto = new DemandaDto(null, estagiario.getId(), "Atendido", "12/11/2025");
		
		Demanda demanda = demandaService.salvar(demandaDto);
		
		assertNotNull(demanda);
		assertNotNull(demanda.getId());
		assertNotNull(demanda.getRegistro());
		assertEquals(demanda.getEstagiario(), estagiario);
		assertEquals(demanda.getPrazo().toString(), "2025-11-12");

		assertEquals(1, demandaRepository.count());
	}
}
