package com.advocacia.estacio.domain.dto;

public class DemandaStatusDto {

	private String demandaStatusAluno;

	private String demandaStatusProfessor;

	private String demandaStatusAdvogado;

	public DemandaStatusDto() {
	}

	public DemandaStatusDto(String demandaStatusAluno, String demandaStatusProfessor, String demandaStatusAdvogado) {
		this.demandaStatusAluno = demandaStatusAluno;
		this.demandaStatusProfessor = demandaStatusProfessor;
		this.demandaStatusAdvogado = demandaStatusAdvogado;
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

	@Override
	public String toString() {
		return "DemandaStatusDto{" +
				"demandaStatusAluno='" + demandaStatusAluno + '\'' +
				", demandaStatusProfessor='" + demandaStatusProfessor + '\'' +
				", demandaStatusAdvogado='" + demandaStatusAdvogado + '\'' +
				'}';
	}
}
