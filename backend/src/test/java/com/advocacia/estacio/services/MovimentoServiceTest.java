package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Movimento;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.MovimentoRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimentoServiceTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	MovimentoService movimentoService;
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Movimento No Banco de Dados Pelo Service")
	void salvar_movimento() {
		assertEquals(0, movimentoRepository.count());
		
		Long assistidoId = assistidoService.salvar(testUtil.getAssistidoDto()).getId();
		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		Long estagiarioId = estagiarioRepository.save(testUtil.getEstagiario()).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId, estagiarioId, "Civil", "Estadual", "25/10/2025");
		
		Long processoId = processoService.salvar(request).getId();
		
		MovimentoDto movimentoDto = new MovimentoDto(null, processoId, advogadoId, "Seguro de Carro");
		
		Movimento movimento = movimentoService.salvar(movimentoDto);
		
		assertNotNull(movimento);
		assertNotNull(movimento.getId());
		assertEquals(advogadoId, movimento.getAdvogado().getId().intValue());
		assertEquals(processoId, movimento.getProcesso().getId().intValue());
		assertEquals("Seguro de Carro", movimento.getMovimento());

		assertEquals(1, movimentoRepository.count());
	}
}
