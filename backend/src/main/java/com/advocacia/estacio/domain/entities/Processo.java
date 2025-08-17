package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.enums.StatusProcesso;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_processo")
public class Processo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer numeroDoProcesso;
	
	private String assunto;
	
	private LocalDateTime prazoFinal;
	
	private String responsavel;
	
	@Enumerated(EnumType.STRING)
	private StatusProcesso statusDoProcesso;
	
	private String partesEnvolvidas;
	
	private LocalDateTime ultimaAtualizacao;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Processo() {
	}

	public Processo(Long id, Integer numeroDoProcesso, String assunto, LocalDateTime prazoFinal, String responsavel,
			StatusProcesso statusDoProcesso, String partesEnvolvidas, LocalDateTime ultimaAtualizacao) {
		this.id = id;
		this.numeroDoProcesso = numeroDoProcesso;
		this.assunto = assunto;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.statusDoProcesso = statusDoProcesso;
		this.partesEnvolvidas = partesEnvolvidas;
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	
	public Processo(ProcessoDto dto) {
		this.numeroDoProcesso = dto.getNumeroDoProcesso();
		this.assunto = dto.getAssunto();
		this.prazoFinal = dto.getPrazoFinal();
		this.responsavel = dto.getResponsavel();
		this.statusDoProcesso = StatusProcesso.toEnum(dto.getStatusDoProcesso());
		this.partesEnvolvidas = dto.getPartesEnvolvidas();
		this.ultimaAtualizacao = dto.getUltimaAtualizacao();
	}
	
	public Processo(ProcessoRequestDto request) {
		this.assunto = request.getAssunto();
		this.responsavel = request.getResponsavel();
		this.partesEnvolvidas = request.getPartesEnvolvidas();
	}
	
	public void postulatoria() {
		this.statusDoProcesso = StatusProcesso.POSTULATORIA;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroDoProcesso() {
		return numeroDoProcesso;
	}

	public void setNumeroDoProcesso(Integer numeroDoProcesso) {
		this.numeroDoProcesso = numeroDoProcesso;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public LocalDateTime getPrazoFinal() {
		return prazoFinal;
	}

	public void setPrazoFinal(LocalDateTime prazoFinal) {
		this.prazoFinal = prazoFinal;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public StatusProcesso getStatusDoProcesso() {
		return statusDoProcesso;
	}

	public void setStatusDoProcesso(StatusProcesso statusDoProcesso) {
		this.statusDoProcesso = statusDoProcesso;
	}

	public String getPartesEnvolvidas() {
		return partesEnvolvidas;
	}

	public void setPartesEnvolvidas(String partesEnvolvidas) {
		this.partesEnvolvidas = partesEnvolvidas;
	}

	public LocalDateTime getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assunto, id, numeroDoProcesso, partesEnvolvidas, prazoFinal, responsavel, statusDoProcesso,
				ultimaAtualizacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		return Objects.equals(assunto, other.assunto) && Objects.equals(id, other.id)
				&& Objects.equals(numeroDoProcesso, other.numeroDoProcesso)
				&& Objects.equals(partesEnvolvidas, other.partesEnvolvidas)
				&& Objects.equals(prazoFinal, other.prazoFinal) && Objects.equals(responsavel, other.responsavel)
				&& Objects.equals(statusDoProcesso, other.statusDoProcesso)
				&& Objects.equals(ultimaAtualizacao, other.ultimaAtualizacao);
	}
}
