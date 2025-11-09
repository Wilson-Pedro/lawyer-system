package com.advocacia.estacio.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.enums.TipoDoAtor;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "tbl_ator")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private TipoDoAtor tipoDoAtor;
	
	private String senha;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Ator() {
	}

	public Ator(String nome, String email, TipoDoAtor tipoDoAtor, String senha) {
		this.nome = nome;
		this.email = email;
		this.tipoDoAtor = tipoDoAtor;
		this.senha = senha;
	}
	
	public Ator(AtorDto dto) {
		this.nome = dto.getNome();
		this.email = dto.getEmail();
		this.tipoDoAtor = TipoDoAtor.toEnum(dto.getTipoAtor());
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

	public TipoDoAtor getTipoDoAtor() {
		return tipoDoAtor;
	}

	public void setTipoDoAtor(TipoDoAtor tipoDoAtor) {
		this.tipoDoAtor = tipoDoAtor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public LocalDateTime getRegistro() {
		return registro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, nome, registro, senha, tipoDoAtor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ator other = (Ator) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(registro, other.registro) && Objects.equals(senha, other.senha)
				&& tipoDoAtor == other.tipoDoAtor;
	}
}
