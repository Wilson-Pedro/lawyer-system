package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.services.AdvogadoService;

@SpringBootTest
class AdvogadoExceptionTest {
	
	@Autowired
	AdvogadoService advogadoService;

	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_advogadoPorId() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> advogadoService.buscarPorId(70L));
	}
}
