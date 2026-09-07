package com.advocacia.estacio.modules.processos;

public enum ProcessoStatus {
	
	TRAMITANDO("Tramitando"),
	SUSPENSO("Suspenso"),
	ARQUIVADO("Arquivado"),
	TODOS("Todos");

	private final String descricao;

	ProcessoStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(ProcessoStatus processoStatus) {
		if (processoStatus == null) {
			return null;
		}
		return processoStatus.getDescricao();
	}
}
