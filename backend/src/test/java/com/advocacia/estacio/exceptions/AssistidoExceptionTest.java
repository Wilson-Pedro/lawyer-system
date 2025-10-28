package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.services.AssistidoService;

@SpringBootTest
class AssistidoExceptionTest {
	
	@Autowired
	AssistidoService assistidoService;

	@Test
	void deve_lancar_excecao_EntidadeNaoEncontradaException_ao_buscar_assistidoPorId() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> assistidoService.buscarPorId(70L));
	}
}
