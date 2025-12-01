package com.advocacia.estacio.domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_demanda_responde")
public class DemandaResponde implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "demanda_id")
	private Demanda demanda;

	@ManyToOne
	@JoinColumn(name = "estagiario_id")
	private Estagiario estagiario;

	private String resposta;

	@CreationTimestamp
	private LocalDateTime registro;

	public DemandaResponde() {
	}

	public DemandaResponde(Demanda demanda, Estagiario estagiario, Long id, LocalDateTime registro, String resposta) {
		this.demanda = demanda;
		this.estagiario = estagiario;
		this.id = id;
		this.registro = registro;
		this.resposta = resposta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Demanda getDemanda() {
		return demanda;
	}

	public void setDemanda(Demanda demanda) {
		this.demanda = demanda;
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		DemandaResponde that = (DemandaResponde) o;
		return Objects.equals(id, that.id) && Objects.equals(demanda, that.demanda) && Objects.equals(estagiario, that.estagiario) && Objects.equals(registro, that.registro);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, demanda, estagiario, registro);
	}
}
