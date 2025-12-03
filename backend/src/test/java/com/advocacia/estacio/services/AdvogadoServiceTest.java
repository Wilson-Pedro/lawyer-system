package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdvogadoServiceTest {
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	TestUtil testUtil;
	
	AdvogadoDto advogadoDto;
	
	@BeforeEach
	void setUp() {
		advogadoDto = testUtil.getAdvogadoDto();
	}
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_DepoisDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Advogado No Banco De Dados Pelo Service")
	void salvar_advogado() {
		assertEquals(0, advogadoRepository.count());
		
		Advogado advogado = advogadoService.salvar(advogadoDto);
		
		assertNotNull(advogado);
		assertNotNull(advogado.getId());
		assertEquals("Carlos Silva", advogado.getNome());
		assertEquals("carlos@gmail.com", advogado.getEmail());
		assertEquals("88566519808", advogado.getTelefone());
		assertEquals("1996-09-25", advogado.getDataDeNascimeto().toString());
		
		assertEquals(1, advogadoRepository.count());

	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Buscar Advogado Por Nome Pelo Service")
	void buscar_advogado_pelo_nome() {
		
		String nome = "il";
		
		Page<Advogado> advogados = advogadoService.buscarAdvogado(nome, 0, 10);
		
		assertNotNull(advogados);
		assertEquals(1, advogados.getContent().size());
		assertEquals("Carlos Silva", advogados.getContent().get(0).getNome());
	}
}
