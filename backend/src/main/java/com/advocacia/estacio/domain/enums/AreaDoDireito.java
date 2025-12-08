package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum AreaDoDireito {
	
	CIVIL(1, "Civil"),
	TRABALHO(2, "Trabalho"),
	PREVIDENCIARIO(3, "Previdenciário");
	
	private Integer codigo;
	
	private String descricao;

	private AreaDoDireito(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static AreaDoDireito toEnum(String descricao) {
		return Stream.of(AreaDoDireito.values())
				.filter(p -> p.getDescricao().equals(descricao))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Área De Direito inválido: " + descricao));
	}
	
	public static AreaDoDireito toEnum(Integer codigo) {
		return Stream.of(AreaDoDireito.values())
				.filter(p -> p.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código da Area DeDireito inválido: " + codigo));
	}
}
