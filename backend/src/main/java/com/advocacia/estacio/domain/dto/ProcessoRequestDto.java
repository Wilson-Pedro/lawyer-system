package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

public class ProcessoRequestDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String assunto;
	
	private String responsavel;
	
	private String partesEnvolvidas;
	
	public ProcessoRequestDto() {
	}

	public ProcessoRequestDto(String assunto, String responsavel, String partesEnvolvidas) {
		this.assunto = assunto;
		this.responsavel = responsavel;
		this.partesEnvolvidas = partesEnvolvidas;
	}

	public String getAssunto() {
		return assunto;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public String getPartesEnvolvidas() {
		return partesEnvolvidas;
	}
}
