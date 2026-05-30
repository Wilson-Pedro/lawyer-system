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

	private String professorNome;

	private String advogadoNome;
	
	private Long estagiarioId;

	private Long professorId;

	private Long advogadoId;
	
	private String demandaStatusAluno;

	private String demandaStatusProfessor;

	private String demandaStatusAdvogado;

	private String prazoDocumentos;

	private String prazo;

	private Integer diasPrazo;

	private String tempestividade;

	public DemandaDto() {
	}

	public DemandaDto(Long id, String demanda, String estagiarioNome, Long estagiarioId, Long advogadoId,
					  DemandaStatus demandaStatusAluno, DemandaStatus demandaStatusProfessor, DemandaStatus demandaStatusAdvogado, LocalDate prazoDocumentos,
					  LocalDate prazo, Tempestividade tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioId = estagiarioId;
		this.estagiarioNome = estagiarioNome;
		this.advogadoId = advogadoId;
		this.demandaStatusAluno = demandaStatusAluno.getStatus();
		this.demandaStatusProfessor = demandaStatusProfessor.getStatus();
		this.demandaStatusAdvogado = demandaStatusAdvogado.getStatus();
		this.prazoDocumentos = Utils.localDateToString(prazoDocumentos);
		this.prazo = Utils.localDateToString(prazo);
		this.tempestividade = tempestividade.getStatus();
	}

	public DemandaDto(Long id, String demanda, String estagiarioNome, String advogadoNome, String professorNome,
					  Long estagiarioId, Long advogadoId, Long professorId,
					  DemandaStatus demandaStatusAluno,  DemandaStatus demandaStatusProfessor, DemandaStatus demandaStatusAdvogado,
					  LocalDate prazoDocumentos,
					  LocalDate prazo, Tempestividade tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioNome = estagiarioNome;
		this.advogadoNome = advogadoNome;
		this.professorNome = professorNome;
		this.estagiarioId = estagiarioId;
		this.advogadoId = advogadoId;
		this.professorId = professorId;
		this.demandaStatusAluno = demandaStatusAluno.getStatus();
		this.demandaStatusProfessor = demandaStatusProfessor.getStatus();
		this.demandaStatusAdvogado = demandaStatusAdvogado.getStatus();
		this.prazoDocumentos = Utils.localDateToString(prazoDocumentos);
		this.prazo = Utils.localDateToString(prazo);
		this.tempestividade = tempestividade.getStatus();
	}

	public DemandaDto(Long id, String demanda,
					  Long estagiarioId, Long professorId, Long advogadoId,
					  String demandaStatusAluno, String demandaStatusProfessor, String demandaStatusAdvogado,
					  String prazoDocumentos,
					  Integer diasPrazo, String tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioId = estagiarioId;
		this.advogadoId = advogadoId;
		this.professorId = professorId;
		this.demandaStatusAluno = demandaStatusAluno;
		this.demandaStatusProfessor = demandaStatusProfessor;
		this.demandaStatusAdvogado = demandaStatusAdvogado;
		this.prazoDocumentos = prazoDocumentos;
		this.diasPrazo = diasPrazo;
		this.tempestividade = tempestividade;
	}

	public DemandaDto(Long id, String demanda,
					  Long estagiarioId, Long professorId, Long advogadoId,
					  String prazoDocumentos,
					  Integer diasPrazo, String tempestividade) {
		this.id = id;
		this.demanda = demanda;
		this.estagiarioId = estagiarioId;
		this.advogadoId = advogadoId;
		this.professorId = professorId;
		this.prazoDocumentos = prazoDocumentos;
		this.diasPrazo = diasPrazo;
		this.tempestividade = tempestividade;
	}
	
	public DemandaDto(Demanda demanda) {
		this.id = demanda.getId();
		this.demanda = demanda.getDemanda();
		this.estagiarioNome = demanda.getEstagiario().getNome();
		this.professorNome = demanda.getProfessor().getNome();
		this.advogadoNome = demanda.getAdvogado().getNome();
		this.estagiarioId = demanda.getEstagiario().getId();
		this.advogadoId = demanda.getAdvogado().getId();
		this.demandaStatusAluno = demanda.getDemandaStatusAluno().getStatus();
		this.demandaStatusProfessor = demanda.getDemandaStatusProfessor().getStatus();
		this.demandaStatusAdvogado = demanda.getDemandaStatusAdvogado().getStatus();
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

	public String getProfessorNome() {
		return professorNome;
	}

	public String getAdvogadoNome() {
		return advogadoNome;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}

	public Long getProfessorId() {
		return professorId;
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

	public String getDemandaStatusAdvogado() {
		return demandaStatusAdvogado;
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

	@Override
	public String toString() {
		return "DemandaDto{" +
				"id=" + id +
				", demanda='" + demanda + '\'' +
				", estagiarioNome='" + estagiarioNome + '\'' +
				", professorNome='" + professorNome + '\'' +
				", advogadoNome='" + advogadoNome + '\'' +
				", estagiarioId=" + estagiarioId +
				", professorId=" + professorId +
				", advogadoId=" + advogadoId +
				", demandaStatusAluno='" + demandaStatusAluno + '\'' +
				", demandaStatusProfessor='" + demandaStatusProfessor + '\'' +
				", demandaStatusAdvogado='" + demandaStatusAdvogado + '\'' +
				", prazoDocumentos='" + prazoDocumentos + '\'' +
				", prazo='" + prazo + '\'' +
				", diasPrazo=" + diasPrazo +
				", tempestividade='" + tempestividade + '\'' +
				'}';
	}
}
