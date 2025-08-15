package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.util.Objects;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_estagiario")
public class Estagiario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String matricula;
	
	@Enumerated(EnumType.STRING)
	private PeriodoEstagio periodo;
	
	private String senha;
	
	public Estagiario() {
	}

	public Estagiario(String nome, String email, String matricula, PeriodoEstagio periodo, String senha) {
		this.nome = nome;
		this.email = email;
		this.matricula = matricula;
		this.periodo = periodo;
		this.senha = senha;
	}
	
	public Estagiario(EstagiarioDto dto) {
		this.nome = dto.getNome();
		this.email = dto.getEmail();
		this.matricula = dto.getMatricula();
		this.periodo = PeriodoEstagio.toEnum(dto.getPeriodo());
		this.senha = dto.getSenha();
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

	public PeriodoEstagio getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodoEstagio periodo) {
		this.periodo = periodo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, matricula, nome, periodo, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estagiario other = (Estagiario) obj;
		return Objects.equals(email, other.email) && Objects.equals(matricula, other.matricula)
				&& Objects.equals(nome, other.nome) && Objects.equals(periodo, other.periodo)
				&& Objects.equals(senha, other.senha);
	}
}
