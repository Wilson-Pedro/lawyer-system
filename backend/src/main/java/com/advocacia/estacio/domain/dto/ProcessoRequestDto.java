package com.advocacia.estacio.domain.dto;

import static com.advocacia.estacio.utils.Utils.localDateToString;

import java.io.Serializable;

import com.advocacia.estacio.domain.entities.Processo;

public class ProcessoRequestDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long assistidoId;
	
	private String numeroDoProcessoPje;
	
	private String assunto;
	
	private String vara;
	
	private String responsavel;
	
	private Long advogadoId;
	
	private Long estagiarioId;
	
	private String areaDoDireito;
	
	private String tribunal;
	
	private String prazo;
	
	public ProcessoRequestDto() {
	}

	public ProcessoRequestDto(Long assistidoId, String numeroDoProcessoPje, String assunto, String vara,
			String responsavel, Long advogadoId, Long estagiarioId, String areaDoDireito, String tribunal,
			String prazo) {
		this.assistidoId = assistidoId;
		this.numeroDoProcessoPje = numeroDoProcessoPje;
		this.assunto = assunto;
		this.vara = vara;
		this.responsavel = responsavel;
		this.advogadoId = advogadoId;
		this.estagiarioId = estagiarioId;
		this.areaDoDireito = areaDoDireito;
		this.tribunal = tribunal;
		this.prazo = prazo;
	}
	
	public ProcessoRequestDto(Processo processo) {
		this.assistidoId = processo.getAssistido().getId();
		this.numeroDoProcessoPje = processo.getNumeroDoProcessoPje();
		this.assunto = processo.getAssunto();
		this.vara = processo.getVara();
		this.responsavel = processo.getResponsavel();
		this.advogadoId = processo.getAdvogado().getId();
		this.estagiarioId = processo.getEstagiario().getId();
		this.areaDoDireito = processo.getAreaDoDireito().getDescricao();
		this.tribunal = processo.getTribunal().getDescricao();
		this.prazo = localDateToString(processo.getPrazoFinal());
	}

	public Long getAssistidoId() {
		return assistidoId;
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

	public String getPrazo() {
		return prazo;
	}
}
