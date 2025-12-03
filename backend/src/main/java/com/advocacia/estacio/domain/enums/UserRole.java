package com.advocacia.estacio.domain.enums;

import com.advocacia.estacio.exceptions.EnumException;

import java.util.stream.Stream;

public enum UserRole {

	ADMIN(1, "admin"),
	COORDENADOR_DO_CURSO(2, "Coordenador do curso"),
	SECRETARIO(3, "Secretário"),
	PROFESSOR(4, "Professor"),
	ESTAGIARIO(5, "Estagiário");
	
	private int codigo;
	
	private String role;
	
	private UserRole(int codigo, String role) {
		this.codigo = codigo;
		this.role = role;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getRole() {
		return role;
	}
	
	public static UserRole toEnum(TipoDoAtor tipoAtor) {
		return Stream.of(UserRole.values())
				.filter(role -> role.getRole().equals(tipoAtor.getTipo()))
				.findFirst()
				.orElseThrow(() -> new EnumException("role inválida"));
	}
}
