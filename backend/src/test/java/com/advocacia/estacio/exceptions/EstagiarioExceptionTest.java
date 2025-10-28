package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.services.EstagiarioService;

@SpringBootTest
class EstagiarioExceptionTest {
	
	@Autowired
	EstagiarioService estagiarioService;

	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_estagiarioPorId() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> estagiarioService.buscarPorId(70L));
	}
}
