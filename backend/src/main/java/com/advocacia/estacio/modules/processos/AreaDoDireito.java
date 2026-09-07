package com.advocacia.estacio.modules.processos;

public enum AreaDoDireito {
	
	CIVIL( "Direito Civil"),
	TRABALHISTA("Direito Trabalhista"),
	PREVIDENCIARIO("Direito Previdenciário");

//	TODO: Verificar necessidade da adição das demais áreas
//	FAMILIA_E_SUCESSOES("Direito de Família e Sucessões"),
//	CONSUMIDOR("Direito do Consumidor"),
//	PENAL("Direito Penal"),
//	TRIBUTARIO("Direito Tributário"),
//	EMPRESARIAL("Direito Empresarial"),
//	ADMINISTRATIVO("Direito Administrativo"),
//	CONSTITUCIONAL("Direito Constitucional"),
//	DIGITAL("Direito Digital"),
//	AMBIENTAL("Direito Ambiental"),
//	ELEITORAL("Direito Eleitoral"),
//	INTERNACIONAL("Direito Internacional");

	private final String descricao;

	AreaDoDireito(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(AreaDoDireito areaDoDireito) {
		if (areaDoDireito == null) {
			return null;
		}
		return areaDoDireito.getDescricao();
	}
}
