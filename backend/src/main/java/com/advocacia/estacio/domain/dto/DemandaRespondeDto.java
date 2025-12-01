package com.advocacia.estacio.domain.dto;

import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.DemandaResponde;

public class DemandaRespondeDto {

	private Long id;

	private Long demandaId;

	private String estagiarioNome;

	private Long estagiarioId;

	private String resposta;

	public DemandaRespondeDto() {
	}

	public DemandaRespondeDto(DemandaResponde demandaResponde) {
		this.demandaId = demandaResponde.getId();
		this.estagiarioId = demandaResponde.getEstagiario().getId();
		this.estagiarioNome = demandaResponde.getEstagiario().getNome();
		this.resposta = demandaResponde.getResposta();
	}

	public DemandaRespondeDto(Long id, Long demandaId, Long estagiarioId, String resposta) {
		this.id = id;
		this.demandaId = demandaId;
		this.estagiarioId = estagiarioId;
		this.resposta = resposta;
	}

	public DemandaRespondeDto(Long id, Long demandaId, Long estagiarioId, String estagiarioNome, String resposta) {
		this(demandaId, estagiarioId, id, resposta);
		this.estagiarioNome = estagiarioNome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDemandaId() {
		return demandaId;
	}

	public void setDemandaId(Long demandaId) {
		this.demandaId = demandaId;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}

	public void setEstagiarioId(Long estagiarioId) {
		this.estagiarioId = estagiarioId;
	}

	public String getEstagiarioNome() {
		return estagiarioNome;
	}

	public void setEstagiarioNome(String estagiarioNome) {
		this.estagiarioNome = estagiarioNome;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
}
