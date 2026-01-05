package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.AdvogadoDto;

@Entity
@Table(name = "tbl_advogado")
public class Advogado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String telefone;
	
	private LocalDate dataDeNascimeto;
	
	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;

	@Enumerated(EnumType.STRING)
	private UsuarioStatus usuarioStatus;

	@OneToOne
	UsuarioAuth usurioAuth;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Advogado() {
	}

	public Advogado(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Advogado(Long id, String nome, String email, String cpf, String telefone, LocalDate dataDeNascimeto, 
			Endereco endereco, LocalDateTime registro) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.dataDeNascimeto = dataDeNascimeto;
		this.endereco = endereco;
		this.registro = registro;
	}
	
	public Advogado(AdvogadoDto dto) {
		this.id = dto.getId();
		this.nome = dto.getNome();
		this.email = dto.getEmail();
		this.telefone = dto.getTelefone();
		this.dataDeNascimeto = localDateToString(dto.getDataDeNascimento());
	}
	
	public AdvogadoDto toDto() {
		return new AdvogadoDto(this);
	}
	
	private LocalDate localDateToString(String string) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(string, formatter);
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

	public LocalDate getDataDeNascimeto() {
		return dataDeNascimeto;
	}

	public void setDataDeNascimeto(LocalDate dataDeNascimeto) {
		this.dataDeNascimeto = dataDeNascimeto;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public UsuarioStatus getUsuarioStatus() {
		return usuarioStatus;
	}

	public void setUsuarioStatus(UsuarioStatus usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}

	public UsuarioAuth getUsurioAuth() {
		return usurioAuth;
	}

	public void setUsurioAuth(UsuarioAuth usurioAuth) {
		this.usurioAuth = usurioAuth;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}

	@Override
	public String toString() {
		return "Advogado [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone
				+ ", dataDeNascimeto=" + dataDeNascimeto + ", endereco=" + endereco + ", registro=" + registro + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataDeNascimeto, email, endereco, id, nome, registro, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Advogado other = (Advogado) obj;
		return Objects.equals(dataDeNascimeto, other.dataDeNascimeto) && Objects.equals(email, other.email)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(registro, other.registro)
				&& Objects.equals(telefone, other.telefone);
	}
}
