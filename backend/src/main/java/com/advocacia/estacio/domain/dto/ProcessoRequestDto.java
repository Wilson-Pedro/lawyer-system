package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

public class ProcessoRequestDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long assistidoId;
	
	private String assunto;
	
	private String vara;
	
	private String responsavel;
	
	private String prazo;
	
	public ProcessoRequestDto() {
	}

	public ProcessoRequestDto(Long assistidoId, String assunto, String vara, String responsavel, String prazo) {
		this.assistidoId = assistidoId;
		this.assunto = assunto;
		this.vara = vara;
		this.responsavel = responsavel;
		this.prazo = prazo;
	}

	public Long getAssistidoId() {
		return assistidoId;
	}

	public String getAssunto() {
		return assunto;
	}

	public String getVara() {
		return vara;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public String getPrazo() {
		return prazo;
	}
}
