package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.enums.StatusProcesso;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_processo")
public class Processo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "assistido_id")
	private Assistido assistido;
	
	@Column(unique = true)
	private String numeroDoProcesso;
	
	private String assunto;
	
	private String vara;
	
	private LocalDate prazoFinal;
	
	private String responsavel;
	
	@ManyToOne
	@JoinColumn(name = "advogado_id")
	private Advogado advogado;
	
	@Enumerated(EnumType.STRING)
	private StatusProcesso statusDoProcesso;
	
	private String partesEnvolvidas;
	
	private LocalDateTime ultimaAtualizacao;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Processo() {
	}

	public Processo(Long id, Assistido assistido, String numeroDoProcesso, String assunto, 
			String vara, LocalDate prazoFinal, String responsavel, Advogado advogado,
			StatusProcesso statusDoProcesso, String partesEnvolvidas, LocalDateTime ultimaAtualizacao) {
		this.id = id;
		this.assistido = assistido;
		this.numeroDoProcesso = numeroDoProcesso;
		this.assunto = assunto;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.advogado = advogado;
		this.statusDoProcesso = statusDoProcesso;
		this.partesEnvolvidas = partesEnvolvidas;
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	
	public Processo(ProcessoDto dto) {
		this.numeroDoProcesso = dto.getNumeroDoProcesso();
		this.assunto = dto.getAssunto();
		this.vara = dto.getVara();
		this.prazoFinal = localDateToString(dto.getPrazoFinal());
		this.responsavel = dto.getResponsavel();
		this.statusDoProcesso = StatusProcesso.toEnum(dto.getStatusDoProcesso());
		this.partesEnvolvidas = dto.getPartesEnvolvidas();
		this.ultimaAtualizacao = dto.getUltimaAtualizacao();
	}
	
	public Processo(ProcessoRequestDto request) {
		this.assunto = request.getAssunto();
		this.vara = request.getVara();
		this.responsavel = request.getResponsavel();
		this.prazoFinal = localDateToString(request.getPrazo());
	}
	
	public void postulatoria() {
		this.statusDoProcesso = StatusProcesso.POSTULATORIA;
	}
	
	private LocalDate localDateToString(String string) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(string, formatter);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Assistido getAssistido() {
		return assistido;
	}

	public void setAssistido(Assistido assistido) {
		this.assistido = assistido;
	}

	public String getNumeroDoProcesso() {
		return numeroDoProcesso;
	}

	public void setNumeroDoProcesso(String numeroDoProcesso) {
		this.numeroDoProcesso = numeroDoProcesso;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getVara() {
		return vara;
	}

	public void setVara(String vara) {
		this.vara = vara;
	}

	public LocalDate getPrazoFinal() {
		return prazoFinal;
	}

	public void setPrazoFinal(LocalDate prazoFinal) {
		this.prazoFinal = prazoFinal;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Advogado getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Advogado advogado) {
		this.advogado = advogado;
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
