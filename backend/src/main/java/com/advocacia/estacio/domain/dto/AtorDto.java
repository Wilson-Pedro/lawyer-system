package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

import com.advocacia.estacio.domain.entities.Ator;

public class AtorDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String tipoAtor;
	
	private String senha;
	
	public AtorDto() {
	}

	public AtorDto(Long id, String nome, String email, String tipoAtor, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipoAtor = tipoAtor;
		this.senha = senha;
	}
	
	public AtorDto(Ator ator) {
		this.id = ator.getId();
		this.nome = ator.getNome();
		this.email = ator.getEmail();
		this.tipoAtor = ator.getTipoDoAtor().getTipo();
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

	public String getTipoAtor() {
		return tipoAtor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
