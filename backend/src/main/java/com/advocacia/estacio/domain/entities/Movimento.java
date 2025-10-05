package com.advocacia.estacio.domain.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.advocacia.estacio.domain.dto.MovimentoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_moviemnto")
public class Movimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "processo_id")
	private Processo processo;
	
	@ManyToOne
	@JoinColumn(name = "advogado_id")
	private Advogado advogado;
	
	private String movimento;
	
	@CreationTimestamp
	private LocalDateTime registro;
	
	public Movimento() {
	}

	public Movimento(Long id, Processo processo, Advogado advogado, String movimento) {
		this.id = id;
		this.processo = processo;
		this.advogado = advogado;
		this.movimento = movimento;
	}
	
	public Movimento(MovimentoDto movimentoDto) {
		this.movimento = movimentoDto.getMovimento();
		this.registro = movimentoDto.getRegistro();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Advogado getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Advogado advogado) {
		this.advogado = advogado;
	}

	public String getMovimento() {
		return movimento;
	}

	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}
}
