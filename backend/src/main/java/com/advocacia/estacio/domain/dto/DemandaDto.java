package com.advocacia.estacio.domain.dto;

import java.time.LocalDate;

import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.domain.enums.Tempestividade;
import com.advocacia.estacio.utils.Utils;

public class DemandaDto {

	private Long id;
	
	private String demanda;
	
	private String estagiarioNome;
	
	private Long estagiarioId;

	private Long advogadoId;
	
	private String demandaStatusAluno;

	private String demandaStatusProfessor;

	private String prazoDocumentos;

	private String prazo;

	private Integer diasPrazo;

	private String tempestividade;

	public DemandaDto() {
	}
	
	public DemandaDto(Long id, String demanda, Long estagiarioId, Long advogadoId, String demandaStatusAluno, String demandaStatusProfessor, String prazoDocumentos, Integer diasPrazo, String tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioId = estagiarioId;
		this.advogadoId = advogadoId;
		this.demandaStatusAluno = demandaStatusAluno;
		this.demandaStatusProfessor = demandaStatusProfessor;
		this.prazoDocumentos = prazoDocumentos;
		this.diasPrazo = diasPrazo;
		this.tempestividade = tempestividade;
	}

	public DemandaDto(Long id, String demanda, String estagiarioNome, Long estagiarioId, Long advogadoId,
					  DemandaStatus demandaStatusAluno,  DemandaStatus demandaStatusProfessor, LocalDate prazoDocumentos, LocalDate prazo, Tempestividade tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioNome = estagiarioNome;
		this.estagiarioId = estagiarioId;
		this.advogadoId = advogadoId;
		this.demandaStatusAluno = demandaStatusAluno.getStatus();
		this.demandaStatusProfessor = demandaStatusProfessor.getStatus();
		this.prazoDocumentos = Utils.localDateToString(prazoDocumentos);
		this.prazo = Utils.localDateToString(prazo);
		this.tempestividade = tempestividade.getStatus();
	}
	
	public DemandaDto(Demanda demanda) {
		this.id = demanda.getId();
		this.demanda = demanda.getDemanda();
		this.estagiarioNome = demanda.getEstagiario().getNome();
		this.estagiarioId = demanda.getEstagiario().getId();
		this.advogadoId = demanda.getAdvogado().getId();
		this.demandaStatusAluno = demanda.getDemandaStatusAluno().getStatus();
		this.demandaStatusProfessor = demanda.getDemandaStatusProfessor().getStatus();
		this.prazoDocumentos = Utils.localDateToString(demanda.getPrazoDocumentos());
		this.prazo = Utils.localDateToString(demanda.getPrazo());
		this.tempestividade = demanda.getTempestividade().getStatus();
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

	public Long getAdvogadoId() {
		return advogadoId;
	}

	public String getDemandaStatusAluno() {
		return demandaStatusAluno;
	}

	public String getDemandaStatusProfessor() {
		return demandaStatusProfessor;
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

	public String getTempestividade() {
		return tempestividade;
	}
}
