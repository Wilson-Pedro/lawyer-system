package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioServiceTest {
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	void deveSalvar_Estagiario_NoBancoDeDadosPeloService() {
		assertEquals(0, estagiarioRepository.count());
		assertEquals(0, usuarioAuthRepository.count());
		
		Estagiario estagiarioSalvo = estagiarioService.salvar(testUtil.getEstagiarioDto());
		
		assertEquals("Pedro Lucas", estagiarioSalvo.getNome());
		assertEquals("pedro@gmail.com", estagiarioSalvo.getEmail());
		assertEquals("20251208", estagiarioSalvo.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiarioSalvo.getPeriodo());
		assertFalse("1234".equals(estagiarioSalvo.getSenha()));
		assertEquals(1, estagiarioRepository.count());

		assertEquals(1, usuarioAuthRepository.count());
	}
}
