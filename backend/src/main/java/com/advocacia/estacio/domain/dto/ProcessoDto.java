package com.advocacia.estacio.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.advocacia.estacio.domain.entities.Processo;

public class ProcessoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Integer numeroDoProcesso;
	
	private String assunto;
	
	private LocalDateTime prazoFinal;
	
	private String responsavel;
	
	private String statusDoProcesso;
	
	private String partesEnvolvidas;
	
	private LocalDateTime ultimaAtualizacao;
	
	public ProcessoDto() {
	}

	public ProcessoDto(Long id, Integer numeroDoProcesso, String assunto, LocalDateTime prazoFinal, String responsavel,
			String statusDoProcesso, String partesEnvolvidas, LocalDateTime ultimaAtualizacao) {
		this.id = id;
		this.numeroDoProcesso = numeroDoProcesso;
		this.assunto = assunto;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.statusDoProcesso = statusDoProcesso;
		this.partesEnvolvidas = partesEnvolvidas;
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	
	public ProcessoDto(Processo processo) {
		this.id = processo.getId();
		this.numeroDoProcesso = processo.getNumeroDoProcesso();
		this.assunto = processo.getAssunto();
		this.prazoFinal = processo.getPrazoFinal();
		this.responsavel = processo.getResponsavel();
		this.statusDoProcesso = processo.getStatusDoProcesso().getStatus();
		this.partesEnvolvidas = processo.getPartesEnvolvidas();
		this.ultimaAtualizacao = processo.getUltimaAtualizacao();
	}

	public Long getId() {
		return id;
	}

	public Integer getNumeroDoProcesso() {
		return numeroDoProcesso;
	}

	public String getAssunto() {
		return assunto;
	}

	public LocalDateTime getPrazoFinal() {
		return prazoFinal;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public String getStatusDoProcesso() {
		return statusDoProcesso;
	}

	public String getPartesEnvolvidas() {
		return partesEnvolvidas;
	}

	public LocalDateTime getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}
}
