package com.advocacia.estacio.services;

import static org.assertj.core.api.Assertions.assertThatException;
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

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdvogadoServiceTest {
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	AdvogadoDto advogadoDto;
	
	@BeforeEach
	void setUp() {
		advogadoDto = new AdvogadoDto(null, "Júlio Silva", "julio@gmail.com", "61946620131",
				"88566519808", "25/09/1996", "São Luís", "Vila Palmeira", 
				"rua dos nobres", 12, "43012-232");
	}

	@Test
	@Order(1)
	void deveSalvar_Advogado_NoBancoDeDados_PeloService() {
		assertEquals(0, advogadoRepository.count());
		
		Advogado advogado = advogadoService.salvar(advogadoDto);
		
		assertNotNull(advogado);
		assertNotNull(advogado.getId());
		assertEquals("Júlio Silva", advogado.getNome());
		assertEquals("julio@gmail.com", advogado.getEmail());
		assertEquals("61946620131", advogado.getCpf());
		assertEquals("88566519808", advogado.getTelefone());
		assertEquals("1996-09-25", advogado.getDataDeNascimeto().toString());
		
		assertEquals(1, advogadoRepository.count());

	}
	
	@Test
	@Order(2)
	void deveBuscar_Advogado_peloNome_PeloService() {
		
		String nome = "il";
		
		Page<Advogado> advogados = advogadoService.buscarAdvogado(nome, 0, 10);
		
		assertNotNull(advogados);
		assertEquals(advogados.getContent().size(), 1);
		assertEquals(advogados.getContent().get(0).getNome(), "Júlio Silva");

	}

	@Test
	@Order(3)
	void deletandoTodosOsDadosAntesDostestes() {
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		advogadoRepository.deleteAll();
		enderecoRepository.deleteAll();
		processoRepository.deleteAll();
	}
}
