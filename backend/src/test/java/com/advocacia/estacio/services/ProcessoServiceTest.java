package com.advocacia.estacio.services;

import java.util.List;

import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;

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
	@DisplayName("Deve Salvar Processo No Banco de Dados Pelo Service")
	void salvar_processo() {
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
	@DisplayName("Deve Buscar Processos Por Status No Banco de Dados Pelo Service")
	void buscar_processos_por_status() {
		
		List<ProcessoDto> processos = processoService.buscarProcessosPorStatusDoProcesso("TRAMITANDO");
		
		assertNotNull(processos);
        assertFalse(processos.isEmpty());
	}
	
	@Test
	@DisplayName("Deve Buscar Processo Por Numero Do Processo No Banco de Dados Pelo Status")
	void buscar_processo_por_numero_do_processo() {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso().toString();
		Page<Processo> processos = processoService.buscarProcesso(numeroDoProcesso, 0, 10);
		
		assertNotNull(processos);
        assertFalse(processos.getContent().isEmpty());
		assertNotNull(processos.getContent().get(0).getAssunto(), "Seguro de Carro");
	}
	
	@Test
	@DisplayName("Deve Atualizar Estagiario No Processo Pelo Service")
	void atualizar_estagiario_no_processo() {
		
		assertEquals(1, processoRepository.count());
		
		Processo processo = processoRepository.findAll().get(0);
		Advogado advogado = advogadoService.buscarPorId(processo.getAdvogado().getId());
		Assistido assistido = assistidoService.buscarPorId(processo.getAssistido().getId());
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario2());
		
		ProcessoUpdate dto = new ProcessoUpdate(processo.getAssistido().getId(), 
				"23232323", "32323232", "Seguro de celular", "132132", "11/12/2025", advogado.getNome(), 
				advogado.getId(), estagiario.getId(), "Previdenciário", "Federal", 
				"Tramitando", null);
		
		processo = processoService.atualizarProcesso(processo.getId(), dto);
		
		assertEquals(processo.getAssistido(), assistido);
		assertEquals("23232323", processo.getNumeroDoProcesso());
		assertEquals("32323232", processo.getNumeroDoProcessoPje());
		assertEquals("Seguro de celular", processo.getAssunto());
		assertEquals("132132", processo.getVara());
		assertEquals("2025-12-11", processo.getPrazoFinal().toString());
		assertEquals(processo.getResponsavel(), advogado.getNome());
		assertEquals(processo.getAdvogado(), advogado);
		assertEquals(processo.getEstagiario(), estagiario);
		assertEquals("Previdenciário", processo.getAreaDoDireito().getDescricao());
		assertEquals("Federal", processo.getTribunal().getDescricao());
		assertEquals("Tramitando", processo.getStatusDoProcesso().getStatus());
		
		assertEquals(1, processoRepository.count());
	}
}
