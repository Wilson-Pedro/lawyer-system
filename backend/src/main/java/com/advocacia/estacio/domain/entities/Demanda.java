package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.advocacia.estacio.domain.enums.Tempestividade;
import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.enums.DemandaStatus;

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
@Table(name = "tbl_demanda")
public class Demanda implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String demanda;
	
	@ManyToOne
	@JoinColumn(name = "estagiario_id")
	private Estagiario estagiario;

	@ManyToOne
	@JoinColumn(name = "advogado_id")
	private Advogado advogado;
	
	@Enumerated(EnumType.STRING)
	private DemandaStatus demandaStatus;

	private LocalDate prazoDocumentos;

	private LocalDate prazo;

	@Enumerated(EnumType.STRING)
	private Tempestividade tempestividade;

	@CreationTimestamp
	private LocalDateTime registro;

	public Demanda() {
	}

	public Demanda(Long id, String demanda, Estagiario estagiario, Advogado advogado, DemandaStatus demandaStatus, LocalDate prazoDocumentos, LocalDate prazo, Tempestividade tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiario = estagiario;
		this.advogado = advogado;
		this.demandaStatus = demandaStatus;
		this.prazoDocumentos = prazoDocumentos;
		this.prazo = prazo;
		this.tempestividade = tempestividade;
	}
	
	public Demanda(DemandaDto demandaDto) {
		this.demanda = demandaDto.getDemanda();
		this.demandaStatus = DemandaStatus.toEnum(demandaDto.getDemandaStatus());
		this.prazoDocumentos = localDateToString(demandaDto.getPrazoDocumentos());
		this.tempestividade = Tempestividade.toEnum(demandaDto.getTempestividade());
	}
	
	private LocalDate localDateToString(String string) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(string, formatter);
	}

	public DemandaDto toDto() {
		return new DemandaDto(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDemanda() {
		return demanda;
	}

	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}

	public Advogado getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Advogado advogado) {
		this.advogado = advogado;
	}

	public DemandaStatus getDemandaStatus() {
		return demandaStatus;
	}

	public void setDemandaStatus(DemandaStatus demandaStatus) {
		this.demandaStatus = demandaStatus;
	}

	public LocalDate getPrazoDocumentos() {
		return prazoDocumentos;
	}

	public void setPrazoDocumentos(LocalDate prazoDocumentos) {
		this.prazoDocumentos = prazoDocumentos;
	}

	public LocalDate getPrazo() {
		return prazo;
	}

	public void setRegistro(LocalDateTime registro) {
		this.registro = registro;
	}

	public Tempestividade getTempestividade() {
		return tempestividade;
	}

	public void setTempestividade(Tempestividade tempestividade) {
		this.tempestividade = tempestividade;
	}

	public void setPrazo(LocalDate prazo) {
		this.prazo = prazo;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(demanda, demandaStatus, estagiario, id, prazo, registro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Demanda other = (Demanda) obj;
		return Objects.equals(demanda, other.demanda) && demandaStatus == other.demandaStatus
				&& Objects.equals(estagiario, other.estagiario) && Objects.equals(id, other.id)
				&& Objects.equals(prazo, other.prazo) && Objects.equals(registro, other.registro);
	}
}
