package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.services.ProcessoService;

@SpringBootTest
class ProcessoExceptionTest {
	
	@Autowired
	ProcessoService processoService;

	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_processoPorId() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> processoService.buscarPorId(70L));
	}
	
	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_processoPorNumeroDoProcesso() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> processoService.buscarPorNumeroDoProcesso("123432"));
	}
}
