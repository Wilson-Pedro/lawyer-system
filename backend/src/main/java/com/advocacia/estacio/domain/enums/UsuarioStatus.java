package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum UsuarioStatus {

	ATIVO(1, "Ativo"),
	INATIVO(2, "Inativo");

	private Integer codigo;

	private String descricao;

	private UsuarioStatus(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static UsuarioStatus toEnum(String descricao) {
		return Stream.of(UsuarioStatus.values())
				.filter(p -> p.getDescricao().equals(descricao))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Descriçãqo do usuário status inválido: " + descricao));
	}
	
	public static UsuarioStatus toEnum(Integer codigo) {
		return Stream.of(UsuarioStatus.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Codigo do usuário Status inválido: " + codigo));
	}
}
