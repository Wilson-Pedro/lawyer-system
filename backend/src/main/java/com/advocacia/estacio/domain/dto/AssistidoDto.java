package com.advocacia.estacio.domain.dto;

import java.io.Serializable;

import com.advocacia.estacio.domain.entities.Assistido;

public class AssistidoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private String matricula;
	
	private String telefone;
	
	private String email;
	
	private String profissao;
	
	private String nacionalidade;
	
	private String naturalidade;
	
	private String estadoCivil;
	
	private String cidade;
	
	private String bairro;
	
	private String rua;
	
	private Integer numeroDaCasa;
	
	private String cep;
	
	private AssistidoDto() {
	}

	public AssistidoDto(Long id, String nome, String matricula, String telefone, String email, 
			String profissao, String nacionalidade, String naturalidade, String estadoCivil,
			String cidade, String bairro, String rua, Integer numeroDaCasa, String cep) {
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.telefone = telefone;
		this.email = email;
		this.profissao = profissao;
		this.nacionalidade = nacionalidade;
		this.naturalidade = naturalidade;
		this.estadoCivil = estadoCivil;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numeroDaCasa = numeroDaCasa;
		this.cep = cep;
	}
	
	public AssistidoDto(Assistido assistido) {
		this.id = assistido.getId();
		this.nome = assistido.getNome();
		this.matricula = assistido.getMatricula();
		this.telefone = assistido.getTelefone();
		this.estadoCivil = assistido.getEstadoCivil().getEstado();
		this.email = assistido.getEmail();
		this.profissao = assistido.getProfissao();
		this.nacionalidade = assistido.getNacionalidade();
		this.naturalidade = assistido.getNaturalidade();
		this.cidade = assistido.getEndereco().getCidade();
		this.bairro = assistido.getEndereco().getBairro();
		this.rua = assistido.getEndereco().getRua();
		this.numeroDaCasa = assistido.getEndereco().getNumeroDaCasa();
		this.cep = assistido.getEndereco().getCep();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getMatricula() {
		return matricula;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}

	public String getProfissao() {
		return profissao;
	}
	
	public String getNacionalidade() {
		return nacionalidade;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public String getEstadoCivil() {
		return estadoCivil;
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
