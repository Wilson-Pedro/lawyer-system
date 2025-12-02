package com.advocacia.estacio.domain.dto;

import com.advocacia.estacio.domain.entities.DemandaResponde;
import com.advocacia.estacio.domain.enums.RespondidoPor;

public class DemandaRespondeDto {

	private Long id;

	private Long demandaId;

	private String estagiarioNome;

	private Long estagiarioId;

	private String resposta;

	private String respondidoPor;

	public DemandaRespondeDto() {
	}

	public DemandaRespondeDto(DemandaResponde demandaResponde) {
		this.demandaId = demandaResponde.getId();
		this.estagiarioId = demandaResponde.getEstagiario().getId();
		this.estagiarioNome = demandaResponde.getEstagiario().getNome();
		this.resposta = demandaResponde.getResposta();
		this.respondidoPor = demandaResponde.getRespondidoPor().getDescricao();
	}

	public DemandaRespondeDto(Long id, Long demandaId, Long estagiarioId, String resposta, String respondidoPor) {
		this.id = id;
		this.demandaId = demandaId;
		this.estagiarioId = estagiarioId;
		this.resposta = resposta;
		this.respondidoPor = respondidoPor;
	}

	public DemandaRespondeDto(Long id, Long demandaId, Long estagiarioId, String estagiarioNome, String resposta, RespondidoPor respondidoPor) {
		this.id = id;
		this.demandaId = demandaId;
		this.estagiarioId = estagiarioId;
		this.estagiarioNome = estagiarioNome;
		this.resposta = resposta;
		this.respondidoPor = respondidoPor.getDescricao();
	}

	public DemandaRespondeDto(Long id, Long demandaId, Long estagiarioId, String estagiarioNome, String resposta, String respondidoPor) {
		this(demandaId, estagiarioId, id, resposta, respondidoPor);
		this.estagiarioNome = estagiarioNome;
	}

	public Long getId() {
		return id;
	}

	public Long getDemandaId() {
		return demandaId;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}

	public String getEstagiarioNome() {
		return estagiarioNome;
	}

	public String getRespondidoPor() {
		return respondidoPor;
	}

	public String getResposta() {
		return resposta;
	}
}
