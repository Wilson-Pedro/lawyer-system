package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum DemandaStatus {

	CORRIGIDO(1, "Corrigido"),
	EM_CORRECAO(2, "Em Correção"),
	DEVOLVIDO(3, "Devolvido"),
	DENTRO_DO_PRAZO(4, "Dentro do Prazo"),
	FORA_DO_PRAZO(5, "Fora do Prazo"),
	RECEBIDO(6, "Recebido"),
	PROTOCOLADO(7, "Protocolado");
	
	private Integer codigo;
	
	private String status;
	
	private DemandaStatus(Integer codigo, String status) {
		this.codigo = codigo;
		this.status = status;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getStatus() {
		return status;
	}
	
	public static DemandaStatus toEnum(String status) {
		return Stream.of(DemandaStatus.values())
				.filter(d -> d.getStatus().equals(status))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Status inválido: " + status));
	}
	
	public static DemandaStatus toEnum(Integer codigo) {
		return Stream.of(DemandaStatus.values())
				.filter(d -> d.getCodigo().equals(codigo))
				.findFirst()
				.orElseThrow(() -> new EnumException
						("Código inválido: " + codigo));
	}
}
