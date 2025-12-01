package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.dto.ProcessoUpdate;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoServiceTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
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
	void deveSalvar_Processo_NoBancoDeDados_PeloService() {
		assertEquals(0, processoRepository.count());
		
		Long assistidoId = assistidoService.salvar(testUtil.getAssistidoDto()).getId();
		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		Long estagiarioId = estagiarioRepository.save(testUtil.getEstagiario()).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId, estagiarioId, "Civil", "Estadual", "25/10/2025");
		
		Processo processo = processoService.salvar(request);
		
		assertEquals("Ana Carla", processo.getAssistido().getNome());
		assertEquals("Seguro de Carro", processo.getAssunto());
		assertEquals("23423ee23", processo.getVara());
		assertEquals("2025-10-25", processo.getPrazoFinal().toString());
		assertEquals("Júlio", processo.getResponsavel());
		assertEquals("Carlos Silva", processo.getAdvogado().getNome());
		assertNotNull(processo.getNumeroDoProcesso());

		assertEquals(1, processoRepository.count());
	}
	
	@Test
	void deveBuscar_Processo_PorStatusDoProcesso_PeloService() {
		
		List<ProcessoDto> processos = processoService.buscarProcessosPorStatusDoProcesso("TRAMITANDO");
		
		assertNotNull(processos);
		assertTrue(processos.size() > 0);
	}
	
	@Test
	void deveBuscar_Processo_Pelo_NumeroDoProcesso_PeloService() {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso().toString();
		Page<Processo> processos = processoService.buscarProcesso(numeroDoProcesso, 0, 10);
		
		assertNotNull(processos);
		assertTrue(processos.getContent().size() > 0);
		assertNotNull(processos.getContent().get(0).getAssunto(), "Seguro de Carro");
	}
	
	@Test
	void deveAtualzar_Estagiario_noProcesso_PeloService() {
		
		assertEquals(1, processoRepository.count());
		
		Processo processo = processoRepository.findAll().get(0);
		Advogado advogado = advogadoService.buscarPorId(processo.getAdvogado().getId());
		Assistido assistido = assistidoService.buscarPorId(processo.getAssistido().getId());
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario2());
		
		ProcessoUpdate dto = new ProcessoUpdate(processo.getAssistido().getId(), 
				"23232323", "32323232", "Seguro de celular", "132132", "11/12/2025", advogado.getNome(), 
				advogado.getId(), estagiario.getId(), "Previdenciário", "Federal", 
				"Suspenso", null);
		
		processo = processoService.atualizarProcesso(processo.getId(), dto);
		
		assertEquals(processo.getAssistido(), assistido);
		assertEquals(processo.getNumeroDoProcesso(), "23232323");
		assertEquals(processo.getNumeroDoProcessoPje(), "32323232");
		assertEquals(processo.getAssunto(), "Seguro de celular");
		assertEquals(processo.getVara(), "132132");
		assertEquals(processo.getPrazoFinal().toString(), "2025-12-11");
		assertEquals(processo.getResponsavel(), advogado.getNome());
		assertEquals(processo.getAdvogado(), advogado);
		assertEquals(processo.getEstagiario(), estagiario);
		assertEquals(processo.getAreaDoDireito().getDescricao(), "Previdenciário");
		assertEquals(processo.getTribunal().getDescricao(), "Federal");
		assertEquals(processo.getStatusDoProcesso().getStatus(), "Suspenso");
		
		assertEquals(1, processoRepository.count());
	}
}
