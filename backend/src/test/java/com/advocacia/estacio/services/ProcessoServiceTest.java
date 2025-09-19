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

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoServiceTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	AssistidoService assistidoService;
	
	AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
			"ana@gmail.com", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");

	@Test
	@Order(1)
	void deveSalvar_Processo_NoBancoDeDadosPeloService() {
		assertEquals(0, processoRepository.count());
		
		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "Seguro de Carro", "23423ee23", "Júlio", "25/10/2025");
		
		Processo processo = processoService.salvar(request);
		
		assertEquals("Ana Carla", processo.getAssistido().getNome());
		assertEquals("Seguro de Carro", processo.getAssunto());
		assertEquals("23423ee23", processo.getVara());
		assertEquals("2025-10-25", processo.getPrazoFinal().toString());
		assertEquals("Júlio", processo.getResponsavel());
		assertNotNull(processo.getNumeroDoProcesso());

		assertEquals(1, processoRepository.count());
	}
	
	@Test
	@Order(2)
	void deveBuscar_Processo_PorStatusDoProcesso() {
		
		List<ProcessoDto> processos = processoService.buscarProcessosPorStatusDoProcesso();
		
		assertNotNull(processos);
		assertTrue(processos.size() > 0);

	}
	
	@Test
	@Order(3)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		estagiarioRepository.deleteAll();
		processoRepository.deleteAll();
		assistidoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}

}
