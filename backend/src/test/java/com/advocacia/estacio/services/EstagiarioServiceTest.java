package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.repositories.EstagiarioRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioServiceTest {
	
	Estagiario estagiario;
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@BeforeEach
	void setUp() {
		estagiario = new Estagiario(
				"Pedro Lucas", "pedro@gmail.com", "20251208", 
				PeriodoEstagio.ESTAGIO_I, "1234");
	}
	
	@Test
	@Order(1)
	void deletandoTodosOsDadosAntesDostestes() {
		estagiarioRepository.deleteAll();
	}

	@Test
	@Order(2)
	void deveSalvarEstagiarioNoBancoPeloService() {
		assertEquals(0, estagiarioRepository.count());
		
		Estagiario estagiarioSalvo = estagiarioService.salvar(new EstagiarioDto(estagiario));
		
		assertEquals("Pedro Lucas", estagiarioSalvo.getNome());
		assertEquals("pedro@gmail.com", estagiarioSalvo.getEmail());
		assertEquals("20251208", estagiarioSalvo.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiarioSalvo.getPeriodo());
		assertEquals("1234", estagiarioSalvo.getSenha());
		assertEquals(1, estagiarioRepository.count());

	}

}
