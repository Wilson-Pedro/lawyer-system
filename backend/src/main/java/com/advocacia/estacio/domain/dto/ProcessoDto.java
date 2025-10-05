package com.advocacia.estacio.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.projections.ProcessoProjection;

public class ProcessoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long assistidoId;
	
	private String numeroDoProcesso;
	
	private String assunto;
	
	private String vara;
	
	private String prazoFinal;
	
	private String responsavel;
	
	private Long advogadoId;
	
	private String advogadoNome;
	
	private String statusDoProcesso;
	
	private String partesEnvolvidas;
	
	private LocalDateTime ultimaAtualizacao;
	
	public ProcessoDto() {
	}

	public ProcessoDto(Long id, Long assistidoId, String numeroDoProcesso, String assunto, 
			String vara, String prazoFinal, String responsavel, Long advogadoId, String advogadoNome,
			String statusDoProcesso, String partesEnvolvidas, LocalDateTime ultimaAtualizacao) {
		this.id = id;
		this.assistidoId = assistidoId;
		this.numeroDoProcesso = numeroDoProcesso;
		this.assunto = assunto;
		this.vara = vara;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.advogadoId = advogadoId;
		this.advogadoNome = advogadoNome;
		this.statusDoProcesso = statusDoProcesso;
		this.partesEnvolvidas = partesEnvolvidas;
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	
	public ProcessoDto(Long id, Long assistidoId, String assunto, String vara, String prazoFinal, String responsavel) {
		this.id = id;
		this.assistidoId = assistidoId;
		this.assunto = assunto;
		this.vara = vara;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
	}
	
	public ProcessoDto(Processo processo) {
		this.id = processo.getId();
		this.assistidoId = processo.getAssistido().getId();
		this.numeroDoProcesso = processo.getNumeroDoProcesso();
		this.assunto = processo.getAssunto();
		this.vara = processo.getVara();
		this.prazoFinal = toDateString(processo.getPrazoFinal());
		this.responsavel = processo.getResponsavel();
		this.advogadoId = processo.getAdvogado().getId();
		this.advogadoNome = processo.getAdvogado().getNome();
		this.statusDoProcesso = processo.getStatusDoProcesso().getStatus();
		this.partesEnvolvidas = processo.getPartesEnvolvidas();
		this.ultimaAtualizacao = processo.getUltimaAtualizacao();
	}
	
	public ProcessoDto(ProcessoProjection projection) {
		this.id = projection.getId();
		this.numeroDoProcesso = projection.getNumeroDoProcesso();
		this.assunto = projection.getAssunto();
		this.prazoFinal = projection.getPrazoFinal();
		this.responsavel = projection.getResponsavel();
		this.advogadoId = projection.getAdvogadoId();
		this.advogadoNome = projection.getAdvogadoNome();
	}
	
	private String toDateString(LocalDate localDate) {
		return String.format("%s/%s/%s", localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}

	public Long getId() {
		return id;
	}

	public Long getAssistidoId() {
		return assistidoId;
	}

	public String getNumeroDoProcesso() {
		return numeroDoProcesso;
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

	public String getAdvogadoNome() {
		return advogadoNome;
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
