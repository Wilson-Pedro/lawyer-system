package com.advocacia.estacio.modules.usuarios.enums;

public enum UsuarioRole {
	ADMIN("admin"),
	COORDENADOR_DO_CURSO("Coordenador do curso"),
	SECRETARIO("Secretário"),
	PROFESSOR("Professor"),
	ESTAGIARIO("Estagiário"),
	ADVOGADO("Advogado");

	private final String descricao;

	UsuarioRole(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(UsuarioRole usuarioRole) {
		if (usuarioRole == null) {
			return null;
		}
		return usuarioRole.getDescricao();
	}
}
