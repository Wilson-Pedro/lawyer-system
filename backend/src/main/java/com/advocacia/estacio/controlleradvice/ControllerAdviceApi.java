package com.advocacia.estacio.controlleradvice;

import com.advocacia.estacio.exceptions.EnumException;
import com.advocacia.estacio.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.advocacia.estacio.domain.dto.Problema;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;

import java.util.List;

@ControllerAdvice
public class ControllerAdviceApi {

	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	public ResponseEntity<List<ValidationErrorDto>> handle400Error(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(ValidationErrorDto::new).toList());
	}

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

	@ExceptionHandler(EnumException.class)
	public ResponseEntity<Problema> enumException(EnumException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = new Problema(e.getMessage(),
				status.value(), status);
		return ResponseEntity.status(status).body(problema);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String>  handle500Error(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Ocorreu um erro interno inesperado no servidor. Tente novamente mais tarde.");
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String>  handleBusinessRuleError(ValidationException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	private record ValidationErrorDto(String field, String message) {
		public ValidationErrorDto(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}
