package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum Tempestividade {

	DENTRO_DO_PRAZO(1, "Dentro do Prazo"),
	FORA_DO_PRAZO(2, "Fora do Prazo");

	private Integer codigo;

	private String status;

	private Tempestividade(Integer codigo, String status) {
		this.codigo = codigo;
		this.status = status;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getStatus() {
		return status;
	}
	
	public static Tempestividade toEnum(String status) {
		return Stream.of(Tempestividade.values())
				.filter(d -> d.getStatus().equals(status))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("tempestividade inválido: " + status));
	}
	
	public static Tempestividade toEnum(Integer codigo) {
		return Stream.of(Tempestividade.values())
				.filter(d -> d.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código inválido: " + codigo));
	}
}
