package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum RespondidoPor {

	COORDENADOR_DO_CURSO(1, "Coordenador do curso"),
	PROFESSOR(2, "Professor"),
	ESTAGIARIO(3, "Estagiário");

	private Integer codigo;

	private String descricao;

	private RespondidoPor(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static RespondidoPor toEnum(String descricao) {
		return Stream.of(RespondidoPor.values())
				.filter(d -> d.getDescricao().equals(descricao))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Descrição inválido: " + descricao));
	}
	
	public static RespondidoPor toEnum(Integer codigo) {
		return Stream.of(RespondidoPor.values())
				.filter(d -> d.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código inválido: " + codigo));
	}
}
