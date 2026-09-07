package com.advocacia.estacio.modules.demandas;

public enum EtapaDemanda {
	CORRIGIDO( "Corrigido"),
	EM_CORRECAO( "Em Correção"),
	DEVOLVIDO("Devolvido"),
//	DENTRO_DO_PRAZO("Dentro do Prazo"),
//	FORA_DO_PRAZO("Fora do Prazo"),
	RECEBIDO ("Recebido"),
	PROTOCOLADO("Protocolado"),
	AGUARDANDO_PROFESSOR( "Aguardando Professor"),
	AGUARDANDO_ADVOGADO("Aguardando Advogado"),
	AGUARDANDO_ALUNO("Aguardando Aluno");

//  TODO: verificar possível uso
//	TRIAGEM("Em Triagem"),
//	AGUARDANDO_DOCUMENTOS("Aguardando Documentos"),
//	EM_ANALISE("Em Análise"),
//	RESOLVIDO_EXTRAJUDICIAL("Resolvido Extrajudicialmente"),
//	ORIENTACAO_FINALIZADA("Orientação Finalizada"),
//	JUDICIALIZADO("Ajuizado / Virou Processo"),
//	ENCERRADO("Encerrado / Arquivado");

	private final String descricao;
	
	EtapaDemanda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(EtapaDemanda etapaDemanda) {
		if (etapaDemanda == null) {
			return null;
		}
		return etapaDemanda.getDescricao();
	}
}
