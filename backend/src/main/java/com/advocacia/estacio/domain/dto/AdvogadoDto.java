package com.advocacia.estacio.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.advocacia.estacio.domain.entities.Advogado;

public class AdvogadoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String telefone;
	
	private String dataDeNascimeto;
	
	private String cidade;
	
	private String bairro;
	
	private String rua;
	
	private Integer numeroDaCasa;
	
	private String cep;
	
	public AdvogadoDto() {
	}

	public AdvogadoDto(Long id, String nome, String email, String cpf, String telefone, String dataDeNascimeto, String cidade, String bairro, String rua, Integer numeroDaCasa, String cep) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.dataDeNascimeto = dataDeNascimeto;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numeroDaCasa = numeroDaCasa;
		this.cep = cep;
	}

	public AdvogadoDto(Advogado advogado) {
		this.id = advogado.getId();
		this.nome = advogado.getNome();
		this.email = advogado.getEmail();
		this.cpf = advogado.getCpf();
		this.telefone = advogado.getTelefone();
		this.dataDeNascimeto = toDateString(advogado.getDataDeNascimeto());
		this.cidade = advogado.getEndereco().getCidade();
		this.bairro = advogado.getEndereco().getBairro();
		this.rua = advogado.getEndereco().getRua();
		this.numeroDaCasa = advogado.getEndereco().getNumeroDaCasa();
		this.cep = advogado.getEndereco().getCep();
	}

	private String toDateString(LocalDate localDate) {
		return String.format("%s/%s/%s", localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getDataDeNascimeto() {
		return dataDeNascimeto;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public String getRua() {
		return rua;
	}

	public Integer getNumeroDaCasa() {
		return numeroDaCasa;
	}

	public String getCep() {
		return cep;
	}
}
