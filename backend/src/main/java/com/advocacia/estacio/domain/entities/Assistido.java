package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.util.Objects;

import com.advocacia.estacio.domain.dto.AssistidoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_assistido")
public class Assistido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String matricula;
	
	private String telefone;
	
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	public Assistido() {
	}

	public Assistido(Long id, String nome, String matricula, String telefone, String email, Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
	public Assistido(AssistidoDto dto) {
		this.nome = dto.getNome();
		this.matricula = dto.getMatricula();
		this.telefone = dto.getTelefone();
		this.email = dto.getEmail();
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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, endereco, id, matricula, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assistido other = (Assistido) obj;
		return Objects.equals(email, other.email) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(matricula, other.matricula)
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
	}
}
