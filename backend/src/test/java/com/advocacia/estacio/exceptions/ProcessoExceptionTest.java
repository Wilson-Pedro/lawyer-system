package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.ProcessoService;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoExceptionTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	TestUtil testUtil;
	
	Processo processo;
	
	@BeforeEach
	void setUp() {
		testUtil.deleteAll();
	}

	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_processoPorId() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> processoService.buscarPorId(70L));
	}
	
	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_processoPorNumeroDoProcesso() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> processoService.buscarPorNumeroDoProcesso("123432"));
	}
	
	@Test
	void deve_lancar_excecao_NumeroDoProcessoExistenteException_ao_tentar_validar_NumeroDeProcesso_ja_cadastrado() {

		Processo processo = processoRepository.save(testUtil.getProcesso());
		
		assertTrue(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso()));
		assertThrows(NumeroDoProcessoExistenteException.class, () -> processoService.validarProcesso(processo));
	}
	
	@Test
	void deve_lancar_excecao_NumeroDoProcessoExistenteException_ao_tentar_salvar_NumeroDeProcesso_ja_cadastrado() {

		Processo processo = processoRepository.save(testUtil.getProcesso());
		ProcessoRequestDto request = new ProcessoRequestDto(processo);
		
		assertTrue(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso()));
		assertThrows(NumeroDoProcessoExistenteException.class, () -> testUtil.salvar(request));
	}
}
