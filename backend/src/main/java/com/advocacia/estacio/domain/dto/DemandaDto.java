package com.advocacia.estacio.domain.dto;

import java.time.LocalDate;

import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.enums.DemandaStatus;

public class DemandaDto {

	private Long id;
	
	private String demanda;
	
	private String estagiarioNome;
	
	private Long estagiarioId;
	
	private String demandaStatus;

	private String prazoDocumentos;

	private String prazo;

	private Integer diasPrazo;

	public DemandaDto() {
	}
	
	public DemandaDto(Long id, String demanda, Long estagiarioId, String demandaStatus, String prazoDocumentos, Integer diasPrazo) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioId = estagiarioId;
		this.demandaStatus = demandaStatus;
		this.prazoDocumentos = prazoDocumentos;
		this.diasPrazo = diasPrazo;
	}

	public DemandaDto(Long id, String demanda, String estagiarioNome, Long estagiarioId, 
			DemandaStatus demandaStatus, LocalDate prazoDocumentos, LocalDate prazo) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioNome = estagiarioNome;
		this.estagiarioId = estagiarioId;
		this.demandaStatus = demandaStatus.getStatus();
		this.prazoDocumentos = prazoDocumentos.toString();
		this.prazo = prazo.toString();
	}
	
	public DemandaDto(Demanda demanda) {
		this.id = demanda.getId();
		this.demanda = demanda.getDemanda();
		this.estagiarioNome = demanda.getEstagiario().getNome();
		this.estagiarioId = demanda.getEstagiario().getId();
		this.demandaStatus = demanda.getDemandaStatus().getStatus();
		this.prazoDocumentos = DateToString(demanda.getPrazoDocumentos());
		this.prazo = DateToString(demanda.getPrazo());
	}
	
	private String DateToString(LocalDate date) {
		return String.format("%s/%s/%s", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
	}

	public Long getId() {
		return id;
	}

	public String getDemanda() {
		return demanda;
	}

	public String getEstagiarioNome() {
		return estagiarioNome;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}

	public String getDemandaStatus() {
		return demandaStatus;
	}

	public String getPrazoDocumentos() {
		return prazoDocumentos;
	}

	public String getPrazo() {
		return prazo;
	}

	public Integer getDiasPrazo() {
		return diasPrazo;
	}

	public void setDiasPrazo(Integer diasPrazo) {
		this.diasPrazo = diasPrazo;
	}
}
