package com.advocacia.estacio.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.AssistidoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_endereco")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cidade;
	
	private String bairro;
	
	private String rua;
	
	private Integer numeroDaCasa;
	
	private String cep;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Endereco() {
	}

	public Endereco(Long id, String cidade, String bairro, String rua, Integer numeroDaCasa, String cep) {
		this.id = id;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numeroDaCasa = numeroDaCasa;
		this.cep = cep;
	}
	
	public Endereco(AssistidoDto dto) {
		this.cidade = dto.getCidade();
		this.bairro = dto.getBairro();
		this.rua = dto.getRua();
		this.numeroDaCasa = dto.getNumeroDaCasa();
		this.cep = dto.getCep();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Integer getNumeroDaCasa() {
		return numeroDaCasa;
	}

	public void setNumeroDaCasa(Integer numeroDaCasa) {
		this.numeroDaCasa = numeroDaCasa;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public LocalDateTime getRegistro() {
		return registro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cidade, id, numeroDaCasa, rua);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cidade, other.cidade) && Objects.equals(id, other.id)
				&& Objects.equals(numeroDaCasa, other.numeroDaCasa) && Objects.equals(rua, other.rua);
	}
}
