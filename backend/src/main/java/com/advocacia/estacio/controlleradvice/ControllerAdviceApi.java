package com.advocacia.estacio.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.advocacia.estacio.domain.dto.Problema;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;

@ControllerAdvice
public class ControllerAdviceApi {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Problema> entidadeNaoEncontradaException() {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = new Problema("Entidade Não Encontrada", status.value(), status);
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(NumeroDoProcessoExistenteException.class)
	public ResponseEntity<Problema> numeroDoProcessoExistenteException() {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = new Problema("Esse número do Processo já foi cadastrado", 
				status.value(), status);
		return ResponseEntity.status(status).body(problema);
	}
}
