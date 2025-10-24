package com.advocacia.estacio.domain.dto;

public class ProcessoUpdate {

	private Long id;
	
	private Long estagiarioId;
	
	public ProcessoUpdate() {
	}

	public ProcessoUpdate(Long id, Long estagiarioId) {
		this.id = id;
		this.estagiarioId = estagiarioId;
	}

	public Long getId() {
		return id;
	}

	public Long getEstagiarioId() {
		return estagiarioId;
	}
}
