package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum PeriodoEstagio {
	
	ESTAGIO_I(1, "Estágio I"),
	ESTAGIO_II(2, "Estágio II"),
	ESTAGIO_III(3, "Estágio III"),
	ESTAGIO_IV(4, "Estágio IV");
	
	private Integer codigo;
	
	private String tipo;

	private PeriodoEstagio(Integer codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}
	
	public static PeriodoEstagio toEnum(String tipo) {
		return Stream.of(PeriodoEstagio.values())
				.filter(p -> p.getTipo().equals(tipo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Período inválido: " + tipo));
	}
	
	public static PeriodoEstagio toEnum(Integer codigo) {
		return Stream.of(PeriodoEstagio.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código do Perido inválido: " + codigo));
	}
}
