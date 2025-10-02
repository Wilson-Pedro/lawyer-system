package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

public class ProcessoRequestDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long assistidoId;
	
	private String assunto;
	
	private String vara;
	
	private String responsavel;
	
	private Long advogadoId;
	
	private String prazo;
	
	public ProcessoRequestDto() {
	}

	public ProcessoRequestDto(Long assistidoId, String assunto, String vara, String responsavel, 
			Long advogadoId,  String prazo) {
		this.assistidoId = assistidoId;
		this.assunto = assunto;
		this.vara = vara;
		this.responsavel = responsavel;
		this.advogadoId = advogadoId;
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

	public Long getAdvogadoId() {
		return advogadoId;
	}

	public String getPrazo() {
		return prazo;
	}
}
