package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum StatusProcesso {
	
	TRAMITANDO(1, "Tramitando"),
	SUSPENSO(2, "Suspenso"),
	ARQUIVADO(3, "Arquivado"),
	TODOS(4, "Todos");
	
	private Integer codigo;
	
	private String status;

	private StatusProcesso(Integer codigo, String status) {
		this.codigo = codigo;
		this.status = status;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getStatus() {
		return status;
	}
	
	public static StatusProcesso toEnum(String status) {
		return Stream.of(StatusProcesso.values())
				.filter(p -> p.getStatus().equals(status))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Status inválido: " + status));
	}
	
	public static StatusProcesso toEnum(Integer codigo) {
		return Stream.of(StatusProcesso.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código inválido: " + codigo));
	}
}
