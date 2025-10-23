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

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;
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
	TestUtil testUtil;
	
	AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
			"ana@gmail.com","Cientista de Dados", "brasileiro", "São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	
	AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "julio@gmail.com", "61946620131",
			"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
			"rua do passeio", 11, "53022-112");

	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	void deveSalvar_Processo_NoBancoDeDadosPeloService() {
		assertEquals(0, processoRepository.count());
		
		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
		Long advogadoId = advogadoService.salvar(advogadoDto).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId, "Civil", "Estadual", "25/10/2025");
		
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
	void deveBuscar_Processo_PorStatusDoProcesso() {
		
		List<ProcessoDto> processos = processoService.buscarProcessosPorStatusDoProcesso("TRAMITANDO");
		
		assertNotNull(processos);
		assertTrue(processos.size() > 0);
	}
	
	@Test
	void deveBuscar_Processo_Pelo_NumeroDoProcesso() {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso().toString();
		Page<Processo> processos = processoService.buscarProcesso(numeroDoProcesso, 0, 10);
		
		assertNotNull(processos);
		assertTrue(processos.getContent().size() > 0);
		assertNotNull(processos.getContent().get(0).getAssunto(), "Seguro de Carro");
	}
}
