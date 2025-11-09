package com.advocacia.estacio.domain.enums;

import java.util.stream.Stream;

public enum TipoDoAtor {
	
	COORDENADOR_DO_CURSO(1, "Coordenador do curso"),
	SECRETARIO(2, "Secretário"),
	PROFESSOR(3, "Professor");
	
	private Integer codigo;
	
	private String tipo;

	private TipoDoAtor(Integer codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}
	
	public static TipoDoAtor toEnum(String tipo) {
		return Stream.of(TipoDoAtor.values())
				.filter(p -> p.getTipo().equals(tipo))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Tipo de ator inválido: " + tipo));
	}
	
	public static TipoDoAtor toEnum(Integer codigo) {
		return Stream.of(TipoDoAtor.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Código de ator inválido: " + codigo));
	}
}
