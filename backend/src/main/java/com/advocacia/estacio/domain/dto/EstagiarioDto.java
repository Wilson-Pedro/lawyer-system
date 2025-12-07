package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

import com.advocacia.estacio.domain.entities.Estagiario;

public class EstagiarioDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String matricula;
	
	private String periodo;
	
	private String senha;
	
	public EstagiarioDto() {
	}

	public EstagiarioDto(Long id, String nome, String email, String matricula, String periodo) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.matricula = matricula;
		this.periodo = periodo;
	}
	
	public EstagiarioDto(Long id, String nome, String email, String matricula, String periodo, String senha) {
		this(id, nome, email, matricula, periodo);
		this.senha = senha;
	}
	
	public EstagiarioDto(Estagiario estagiario) {
		this.id = estagiario.getId();
		this.nome = estagiario.getNome();
		this.email = estagiario.getEmail();
		this.matricula = estagiario.getMatricula();
		this.periodo = estagiario.getPeriodo().getTipo();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
