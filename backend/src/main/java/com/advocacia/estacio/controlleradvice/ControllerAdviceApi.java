package com.advocacia.estacio.controlleradvice;

import com.advocacia.estacio.domain.dto.refactorDto.ApiError;
import com.advocacia.estacio.exceptions.EnumException;
import com.advocacia.estacio.exceptions.ValidationException;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerAdviceApi {

	// 1. Erros de Validação (Fields)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handle400Error(MethodArgumentNotValidException ex) {
		List<ApiError.FieldErrorDto> erros = ex.getFieldErrors().stream()
				.map(error -> new ApiError.FieldErrorDto(error.getField(), error.getDefaultMessage()))
				.toList();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Erro de validação nos campos", erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	// 2. Erros de Regra de Negócio Globais
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiError> handleBusinessRuleError(ValidationException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(NumeroDoProcessoExistenteException.class)
	public ResponseEntity<ApiError> numeroDoProcessoExistenteException() {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Esse número do Processo já foi cadastrado");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(EnumException.class)
	public ResponseEntity<ApiError> enumException(EnumException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	// 3. Entidade Não Encontrada (404)
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ApiError> entidadeNaoEncontradaException() {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), "Entidade Não Encontrada");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	// 4. Erros Inesperados do Servidor (500)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handle500Error(Exception ex) {
		ex.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro interno inesperado no servidor. Tente novamente mais tarde.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	}
}