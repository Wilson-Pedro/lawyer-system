package com.advocacia.estacio.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.advocacia.estacio.domain.entities.Movimento;

public class MovimentoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long processoId;
	
	private Long advogadoId;
	
	private String movimento;
	
	private LocalDateTime registro;
	
	public MovimentoDto() {
	}

	public MovimentoDto(Long id, Long processoId, Long advogadoId, String movimento) {
		this.id = id;
		this.processoId = processoId;
		this.advogadoId = advogadoId;
		this.movimento = movimento;
	}
	
	public MovimentoDto(Movimento movimento) {
		this.id = movimento.getId();
		this.processoId = movimento.getProcesso().getId();
		this.advogadoId = movimento.getAdvogado().getId();
		this.movimento = movimento.getMovimento();
		this.registro = movimento.getRegistro();
	}

	public Long getId() {
		return id;
	}

	public Long getProcessoId() {
		return processoId;
	}

	public Long getAdvogadoId() {
		return advogadoId;
	}

	public String getMovimento() {
		return movimento;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}
}
