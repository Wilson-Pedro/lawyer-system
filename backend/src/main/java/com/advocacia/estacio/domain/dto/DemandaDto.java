package com.advocacia.estacio.domain.dto;

import java.time.LocalDate;

import com.advocacia.estacio.domain.entities.Demanda;

public class DemandaDto {

	private Long id;
	
	private Long estagiarioId;
	
	private String demandaStatus;
	
	private String prazo;

	public DemandaDto() {
	}

	public DemandaDto(Long id, Long estagiarioId, String demandaStatus, String prazo) {
		this.id = id;
		this.estagiarioId = estagiarioId;
		this.demandaStatus = demandaStatus;
		this.prazo = prazo;
	}
	
	public DemandaDto(Demanda demanda) {
		this.id = demanda.getId();
		this.estagiarioId = demanda.getEstagiario().getId();
		this.demandaStatus = demanda.getDemandaStatus().getStatus();
		this.prazo = toDateString(demanda.getPrazo());
	}
	
	private String toDateString(LocalDate date) {
		return String.format("%s/%s/%s", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
	}

	public Long getId() {
		return id;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}

	public String getDemandaStatus() {
		return demandaStatus;
	}

	public String getPrazo() {
		return prazo;
	}
}
