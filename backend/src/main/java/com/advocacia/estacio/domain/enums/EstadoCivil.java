package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum EstadoCivil {

	SOLTERIO(1, "Solteiro(a)"),
	CASADO(2, "Casado(a)"), 
	DIVORCIADO(3, "Divorciado(a)"),
	VIUVO(4, "Viuvo(a)"),
	SEPARADO_JUDICIALMENTE(5, "Separado Judicialmente");
	
	private Integer codigo;
	
	private String estado;
	
	private EstadoCivil(Integer codigo, String estado) {
		this.codigo = codigo;
		this.estado = estado;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getEstado() {
		return estado;
	}
	
	public static EstadoCivil toEnum(String estado) {
		return Stream.of(EstadoCivil.values())
				.filter(e -> e.getEstado().equals(estado))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Estado inválido: " + estado));
	}
	
	public static EstadoCivil toEnum(int codigo) {
		return Stream.of(EstadoCivil.values())
				.filter(e -> e.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código inválido: " + codigo));
	}
}
