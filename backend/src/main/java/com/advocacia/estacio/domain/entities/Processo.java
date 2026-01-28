package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.enums.AreaDoDireito;
import com.advocacia.estacio.domain.enums.StatusProcesso;
import com.advocacia.estacio.domain.enums.Tribunal;
import static com.advocacia.estacio.utils.Utils.localDateToString;
import static com.advocacia.estacio.utils.Utils.stringToLocalDateTime;

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
	
	@Column(nullable = true)
	private String numeroDoProcessoPje;
	
	private String assunto;
	
	private String vara;
	
	private LocalDate prazoFinal;
	
	private String responsavel;
	
	@ManyToOne
	@JoinColumn(name = "advogado_id")
	private Advogado advogado;
	
	@ManyToOne
	@JoinColumn(name = "estagiario_id")
	private Estagiario estagiario;
	
	@Enumerated(EnumType.STRING)
	private AreaDoDireito areaDoDireito;
	
	@Enumerated(EnumType.STRING)
	private Tribunal tribunal;
	
	@Enumerated(EnumType.STRING)
	private StatusProcesso statusDoProcesso;
	
	private String partesEnvolvidas;
	
	private LocalDateTime ultimaAtualizacao;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Processo() {
	}
	
	public Processo(Long id, Assistido assistido, String numeroDoProcesso, String numeroDoProcessoPje, String assunto,
			String vara, LocalDate prazoFinal, String responsavel, Advogado advogado, Estagiario estagiario,
			AreaDoDireito areaDoDireito, Tribunal tribunal, StatusProcesso statusDoProcesso, String partesEnvolvidas,
			LocalDateTime ultimaAtualizacao, LocalDateTime registro) {
		this.id = id;
		this.assistido = assistido;
		this.numeroDoProcesso = numeroDoProcesso;
		this.numeroDoProcessoPje = numeroDoProcessoPje;
		this.assunto = assunto;
		this.vara = vara;
		this.prazoFinal = prazoFinal;
		this.responsavel = responsavel;
		this.advogado = advogado;
		this.estagiario = estagiario;
		this.areaDoDireito = areaDoDireito;
		this.tribunal = tribunal;
		this.statusDoProcesso = statusDoProcesso;
		this.partesEnvolvidas = partesEnvolvidas;
		this.ultimaAtualizacao = ultimaAtualizacao;
		this.registro = registro;
	}

	public Processo(ProcessoDto dto) {
		this.numeroDoProcesso = dto.getNumeroDoProcesso();
		this.numeroDoProcessoPje = dto.getNumeroDoProcessoPje();
		this.assunto = dto.getAssunto();
		this.vara = dto.getVara();
		this.prazoFinal = localDateToString(dto.getPrazoFinal());
		this.responsavel = dto.getResponsavel();
		this.areaDoDireito = AreaDoDireito.toEnum(dto.getAreaDoDireito());
		this.tribunal = Tribunal.toEnum(dto.getTribunal());
		this.statusDoProcesso = StatusProcesso.toEnum(dto.getStatusDoProcesso());
		this.partesEnvolvidas = dto.getPartesEnvolvidas();
		this.ultimaAtualizacao = stringToLocalDateTime(dto.getUltimaAtualizacao());
	}
	
	public Processo(ProcessoRequestDto request) {
		this.assunto = request.getAssunto();
		this.numeroDoProcessoPje = request.getNumeroDoProcessoPje();
		this.vara = request.getVara();
		this.responsavel = request.getResponsavel();
		this.prazoFinal = localDateToString(request.getPrazo());
		this.areaDoDireito = AreaDoDireito.toEnum(request.getAreaDoDireito());
		this.tribunal = Tribunal.toEnum(request.getTribunal());
	}
	
	public void tramitando() {
		this.statusDoProcesso = StatusProcesso.TRAMITANDO;
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

	public String getNumeroDoProcessoPje() {
		return numeroDoProcessoPje;
	}

	public void setNumeroDoProcessoPje(String numeroDoProcessoPje) {
		this.numeroDoProcessoPje = numeroDoProcessoPje;
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

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}

	public AreaDoDireito getAreaDoDireito() {
		return areaDoDireito;
	}

	public void setAreaDoDireito(AreaDoDireito areaDoDireito) {
		this.areaDoDireito = areaDoDireito;
	}

	public Tribunal getTribunal() {
		return tribunal;
	}

	public void setTribunal(Tribunal tribunal) {
		this.tribunal = tribunal;
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
