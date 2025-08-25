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

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnderecoServiceTest {
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	AssistidoDto assistidoDto;
	
	@BeforeEach
	void setUp() {
		assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	}

	@Test
	@Order(1)
	void deveSalvar_Endereco_NoBancoDeDados_PeloService() {
		assertEquals(0, enderecoRepository.count());
		
		Endereco endereco = enderecoService.salvar(assistidoDto);
		
		assertNotNull(endereco);
		assertNotNull(endereco.getId());
		assertEquals("São Luís", endereco.getCidade());
		assertEquals("Vila Palmeira", endereco.getBairro());
		assertEquals("rua dos nobres", endereco.getRua());
		assertEquals(12, endereco.getNumeroDaCasa());
		assertEquals("43012-232", endereco.getCep());
		assertEquals(1, enderecoRepository.count());

	}

	@Test
	@Order(2)
	void deletandoTodosOsDadosAntesDostestes() {
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}
}
