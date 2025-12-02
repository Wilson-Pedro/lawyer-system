package com.advocacia.estacio.domain.enums;

import java.util.stream.Stream;

public enum RespondidoPor {

	COORDENADOR_DO_CURSO(1, "Coordenador do curso"),
	PROFESSOR(2, "Professor"),
	ESTAGIARIO(3, "Estagiário");

	private Integer codigo;

	private String descricao;

	private RespondidoPor(Integer codigo, String status) {
		this.codigo = codigo;
		this.descricao = status;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static RespondidoPor toEnum(String status) {
		return Stream.of(RespondidoPor.values())
				.filter(d -> d.getDescricao().equals(status))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Descrição inválido: " + status));
	}
	
	public static RespondidoPor toEnum(Integer codigo) {
		return Stream.of(RespondidoPor.values())
				.filter(d -> d.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Código inválido: " + codigo));
	}
}
