package com.advocacia.estacio.domain.dto;

import org.springframework.http.HttpStatus;

public class Problema {

	private String titulo;
	
	private Integer codigo;
	
	private HttpStatus httpStatus;
	
	public Problema() {
	}
	
	public Problema(String titulo, Integer codigo, HttpStatus httpStatus) {
		this.titulo = titulo;
		this.codigo = codigo;
		this.httpStatus = httpStatus;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
}
