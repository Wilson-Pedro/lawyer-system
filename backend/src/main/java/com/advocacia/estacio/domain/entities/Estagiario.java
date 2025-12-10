package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

	private String telefone;

	private String matricula;
	
	@Enumerated(EnumType.STRING)
	private PeriodoEstagio periodo;
	
	@OneToOne
	private UsuarioAuth usuarioAuth;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Estagiario() {
	}

	public Estagiario(Long id, String nome, String email, String telefone, String matricula, PeriodoEstagio periodo) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.matricula = matricula;
		this.periodo = periodo;
	}


	public Estagiario(String nome, String email, String telefone, String matricula, PeriodoEstagio periodo) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.matricula = matricula;
		this.periodo = periodo;
	}
	
	public Estagiario(EstagiarioDto dto) {
		this.nome = dto.getNome();
		this.email = dto.getEmail();
		this.telefone = dto.getTelefone();
		this.matricula = dto.getMatricula();
		this.periodo = PeriodoEstagio.toEnum(dto.getPeriodo());
	}

	public EstagiarioDto toDto() {
		return new EstagiarioDto(this);
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

	public void setRegistro(LocalDateTime registro) {
		this.registro = registro;
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

	public PeriodoEstagio getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodoEstagio periodo) {
		this.periodo = periodo;
	}

	public String getSenha() {
		return usuarioAuth.getPassword();
	}
	
	public UsuarioAuth getUsuarioAuth() {
		return usuarioAuth;
	}

	public void setUsuarioAuth(UsuarioAuth usuarioAuth) {
		this.usuarioAuth = usuarioAuth;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, matricula, nome, periodo, registro, usuarioAuth);
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
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(matricula, other.matricula) && Objects.equals(nome, other.nome)
				&& periodo == other.periodo && Objects.equals(registro, other.registro)
				&& Objects.equals(usuarioAuth, other.usuarioAuth);
	}
}
