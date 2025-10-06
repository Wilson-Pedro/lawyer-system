package com.advocacia.estacio.domain.dto;

import java.time.LocalDateTime;

import com.advocacia.estacio.domain.entities.Movimento;

public class MovimentoResponseDto {
	
	private Long id;

	private String numeroDoProcesso;
	
	private String advogado;
	
	private String movimento;
	
	private String registro;
	
	public MovimentoResponseDto() {
	}

	public MovimentoResponseDto(Movimento movimento) {
		this.id = movimento.getId();
		this.numeroDoProcesso = movimento.getProcesso().getNumeroDoProcesso();
		this.advogado = movimento.getAdvogado().getNome();
		this.movimento = movimento.getMovimento();
		this.registro = toDateString(movimento.getRegistro());
	}
	
	private String toDateString(LocalDateTime date) {
		return String.format("%s/%s/%s %s:%s:%s", 
				date.getDayOfMonth(), date.getMonthValue(), date.getYear(),
				date.getHour(), date.getMinute(), date.getSecond());
	}

	public Long getId() {
		return id;
	}

	public String getNumeroDoProcesso() {
		return numeroDoProcesso;
	}

	public String getAdvogado() {
		return advogado;
	}

	public String getMovimento() {
		return movimento;
	}

	public String getRegistro() {
		return registro;
	}
}
