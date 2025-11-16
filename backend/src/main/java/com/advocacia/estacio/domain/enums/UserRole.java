package com.advocacia.estacio.domain.enums;

public enum UserRole {

	ADMIN(1, "admin"),
	USER(2, "user");
	
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
}
