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
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnderecoServiceTest {
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	AssistidoDto assistidoDto;
	
	@Autowired
	TestUtil testUtil;
	
	@BeforeEach
	void setUp() {
		assistidoDto = testUtil.getAssistidoDto();
	}
	
	@Test
	@Order(1)
	void deletandoTodosOsDadosAntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
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
}
