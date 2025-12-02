package com.advocacia.estacio.domain.entities;

import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.enums.RespondidoPor;
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

	@Enumerated(EnumType.STRING)
	private RespondidoPor respondidoPor;

	@CreationTimestamp
	private LocalDateTime registro;

	public DemandaResponde() {
	}

	public DemandaResponde(Demanda demanda, Estagiario estagiario, Long id, LocalDateTime registro, String resposta, RespondidoPor respondidoPor) {
		this.demanda = demanda;
		this.estagiario = estagiario;
		this.id = id;
		this.registro = registro;
		this.resposta = resposta;
		this.respondidoPor = respondidoPor;
	}

	public DemandaResponde(DemandaRespondeDto dto) {
		this.resposta = dto.getResposta();
		this.respondidoPor = RespondidoPor.toEnum(dto.getRespondidoPor());
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

	public RespondidoPor getRespondidoPor() {
		return respondidoPor;
	}

	public void setRespondidoPor(RespondidoPor respondidoPor) {
		this.respondidoPor = respondidoPor;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		DemandaResponde that = (DemandaResponde) o;
		return Objects.equals(id, that.id) && Objects.equals(demanda, that.demanda) && Objects.equals(estagiario, that.estagiario) && Objects.equals(resposta, that.resposta) && respondidoPor == that.respondidoPor && Objects.equals(registro, that.registro);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, demanda, estagiario, resposta, respondidoPor, registro);
	}
}
