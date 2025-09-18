package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistidoServiceServiceTest {
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	AssistidoDto assistidoDto;
	
	@BeforeEach
	void setUp() {
		assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	}

	@Test
	@Order(1)
	void deveSalvar_Assistido_NoBancoDeDados_PeloService() {
		assertEquals(0, assistidoRepository.count());
		
		Assistido assistido = assistidoService.salvar(assistidoDto);
		
		assertNotNull(assistido);
		assertNotNull(assistido.getId());
		assertEquals("Ana Carla", assistido.getNome());
		assertEquals("20250815", assistido.getMatricula());
		assertEquals("86766523354", assistido.getTelefone());
		assertEquals("ana@gmail.com", assistido.getEmail());
		
		assertEquals(1, assistidoRepository.count());

	}
	
	@Test
	@Order(2)
	void deveSalvar_buscar_assistido_PeloController() throws Exception {
		
		Page<Assistido> pages = assistidoService.buscarAssistido("Car", 0, 20);
		assertEquals(pages.getContent().size(), 1);
		assertEquals(pages.getContent().get(0).getNome(), "Ana Carla");
		
	}

	@Test
	@Order(3)
	void deletandoTodosOsDadosAntesDostestes() {
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		enderecoRepository.deleteAll();
		processoRepository.deleteAll();
	}
}
