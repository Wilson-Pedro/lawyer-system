package com.advocacia.estacio.domain.dto;

import static com.advocacia.estacio.utils.Utils.localDateTimeToString;
import static com.advocacia.estacio.utils.Utils.localDateToString;

import java.io.Serializable;

import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.projections.ProcessoProjection;

public class ProcessoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long assistidoId;
	
	private String numeroDoProcesso;
	
	private String numeroDoProcessoPje;
	
	private String assunto;
	
	private String vara;
	
	private String prazoFinal;
	
	private String responsavel;
	
	private Long advogadoId;
	
	private Long estagiarioId;
	
	private String advogadoNome;
	
	private String areaDoDireito;
	
	private String tribunal;
	
	private String statusDoProcesso;
	
	private String partesEnvolvidas;
	
	private String ultimaAtualizacao;
	
	public ProcessoDto() {
	}
	
	public ProcessoDto(Long id, Long assistidoId, String numeroDoProcesso, String numeroDoProcessoPje, String assunto,
			String vara, String prazoFinal, String responsavel, Long advogadoId, Long estagiarioId, String advogadoNome,
			String areaDoDireito, String tribunal, String statusDoProcesso, String partesEnvolvidas,
			String ultimaAtualizacao) {
		this.id = id;
		this.assistidoId = assistidoId;
		this.numeroDoProcesso = numeroDoProcesso;
		this.numeroDoProcessoPje = numeroDoProcessoPje;
		this.assunto = assunto;
		this.vara = vara;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.advogadoId = advogadoId;
		this.estagiarioId = estagiarioId;
		this.advogadoNome = advogadoNome;
		this.areaDoDireito = areaDoDireito;
		this.tribunal = tribunal;
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
		this.numeroDoProcessoPje = processo.getNumeroDoProcessoPje();
		this.assunto = processo.getAssunto();
		this.vara = processo.getVara();
		this.prazoFinal = localDateToString(processo.getPrazoFinal());
		this.responsavel = processo.getResponsavel();
		this.advogadoId = processo.getAdvogado().getId();
		this.estagiarioId = processo.getEstagiario().getId();
		this.advogadoNome = processo.getAdvogado().getNome();
		this.areaDoDireito = processo.getAreaDoDireito().getDescricao();
		this.tribunal = processo.getTribunal().getDescricao();
		this.statusDoProcesso = processo.getStatusDoProcesso().getStatus();
		this.partesEnvolvidas = processo.getPartesEnvolvidas();
		this.ultimaAtualizacao = localDateTimeToString(processo.getUltimaAtualizacao());
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

	public Long getId() {
		return id;
	}

	public Long getAssistidoId() {
		return assistidoId;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
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

	public String getAdvogadoNome() {
		return advogadoNome;
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

	public String getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}
}
