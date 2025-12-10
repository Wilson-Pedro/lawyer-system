package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;

public class EstagiarioDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	private String email;

	private String telefone;
	
	private String matricula;
	
	private String periodo;
	
	private String senha;
	
	public EstagiarioDto() {
	}

	public EstagiarioDto(Long id, String nome, String email, String telefone, String matricula, String periodo) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.matricula = matricula;
		this.periodo = periodo;
	}

	public EstagiarioDto(Long id, String nome, String email, String telefone, String matricula, PeriodoEstagio periodo) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.matricula = matricula;
		this.periodo = periodo.getTipo();
	}
	
	public EstagiarioDto(Long id, String nome, String email, String telefone, String matricula, String periodo, String senha) {
		this(id, nome, email, telefone, matricula, periodo);
		this.senha = senha;
	}
	
	public EstagiarioDto(Estagiario estagiario) {
		this.id = estagiario.getId();
		this.nome = estagiario.getNome();
		this.email = estagiario.getEmail();
		this.telefone = estagiario.getTelefone();
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
