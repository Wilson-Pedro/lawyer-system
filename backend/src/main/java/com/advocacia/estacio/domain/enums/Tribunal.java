package com.advocacia.estacio.domain.enums;

import java.util.stream.Stream;

public enum Tribunal {
	
	ESTADUAL(1, "Estadual"),
	FEDERAL(2, "Federal"),
	TRABALHO(3, "Trabalho");
	
	private Integer codigo;
	
	private String descricao;

	private Tribunal(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Tribunal toEnum(String descricao) {
		return Stream.of(Tribunal.values())
				.filter(p -> p.getDescricao().equals(descricao))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Tribunal inválido: " + descricao));
	}
	
	public static Tribunal toEnum(Integer codigo) {
		return Stream.of(Tribunal.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Código de tribunal inválido: " + codigo));
	}
}
