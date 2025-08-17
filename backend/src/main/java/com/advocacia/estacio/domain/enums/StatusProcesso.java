package com.advocacia.estacio.domain.enums;

import java.util.stream.Stream;

public enum StatusProcesso {
	
	POSTULATORIA(1, "Postulatória"),
	INSTRUTORIA(2, "Instrutória"),
	DECISORIA(3, "Decisória"),
	RECURSAL(4, "Recursal"),
	EXECUTOIA(4, "Executória");
	
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
				.orElseThrow(() -> new IllegalArgumentException
						("Status inválido: " + status));
	}
	
	public static StatusProcesso toEnum(Integer codigo) {
		return Stream.of(StatusProcesso.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Código inválido: " + codigo));
	}
}
