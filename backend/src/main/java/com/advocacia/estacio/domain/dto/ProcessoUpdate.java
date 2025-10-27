package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

public class ProcessoUpdate implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long assistidoId;
	
	private String numeroDoProcesso;
	
	private String numeroDoProcessoPje;
	
	private String assunto;
	
	private String vara;
	
	private String prazoFinal;
	
	private String responsavel;
	
	private Long advogadoId;
	
	private Long estagiarioId;
	
	private String areaDoDireito;
	
	private String tribunal;
	
	private String statusDoProcesso;
	
	private String partesEnvolvidas;
	
	public ProcessoUpdate() {
	}
	
	public ProcessoUpdate(Long assistidoId, String numeroDoProcesso, String numeroDoProcessoPje, String assunto,
			String vara, String prazoFinal, String responsavel, Long advogadoId, Long estagiarioId,
			String areaDoDireito, String tribunal, String statusDoProcesso, String partesEnvolvidas) {
		this.assistidoId = assistidoId;
		this.numeroDoProcesso = numeroDoProcesso;
		this.numeroDoProcessoPje = numeroDoProcessoPje;
		this.assunto = assunto;
		this.vara = vara;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.advogadoId = advogadoId;
		this.estagiarioId = estagiarioId;
		this.areaDoDireito = areaDoDireito;
		this.tribunal = tribunal;
		this.statusDoProcesso = statusDoProcesso;
		this.partesEnvolvidas = partesEnvolvidas;
	}

	public Long getAssistidoId() {
		return assistidoId;
	}

	public String getNumeroDoProcesso() {
		return numeroDoProcesso;
	}

	public String getNumeroDoProcessoPje() {
		return numeroDoProcessoPje;
	}

	public String getAssunto() {
		return assunto;
	}

	public String getVara() {
		return vara;
	}

	public String getPrazoFinal() {
		return prazoFinal;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public Long getAdvogadoId() {
		return advogadoId;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}

	public String getAreaDoDireito() {
		return areaDoDireito;
	}

	public String getTribunal() {
		return tribunal;
	}

	public String getStatusDoProcesso() {
		return statusDoProcesso;
	}

	public String getPartesEnvolvidas() {
		return partesEnvolvidas;
	}
}
