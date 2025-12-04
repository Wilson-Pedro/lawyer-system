package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.enums.EstadoCivil;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.utils.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistidoServiceServiceTest {
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
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
	@DisplayName("Deve Salvar Assistido No Banco De Dados Pelo Service")
	void salvar_assistido() {
		assertEquals(0, assistidoRepository.count());
		
		Assistido assistido = assistidoService.salvar(assistidoDto);
		
		assertNotNull(assistido);
		assertNotNull(assistido.getId());
		assertEquals("Ana Carla", assistido.getNome());
		assertEquals("20250815", assistido.getMatricula());
		assertEquals("86766523354", assistido.getTelefone());
		assertEquals("ana@gmail.com", assistido.getEmail());
		assertEquals("Cientista de Dados", assistido.getProfissao());
		assertEquals("brasileiro", assistido.getNacionalidade());
		assertEquals("São Luís/MA", assistido.getNaturalidade());
		assertEquals(EstadoCivil.SOLTERIO, assistido.getEstadoCivil());
		
		assertEquals(1, assistidoRepository.count());

	}
	
	@Test
	@DisplayName("Deve Buscar Assistido Por Nome Pelo Service")
	void buscar_assistido_por_nome() throws Exception {
		
		Page<Assistido> pages = assistidoService.buscarAssistidoPorNome("Car", 0, 20);
		assertEquals(1, pages.getContent().size());
		assertEquals("Ana Carla", pages.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Assistidos")
	void buscar_todos() {

		Page<ResponseMinDto> pages = assistidoService.buscarTodos(0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
	}
}
