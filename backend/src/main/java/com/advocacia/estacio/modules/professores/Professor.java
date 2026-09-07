package com.advocacia.estacio.modules.professores;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tbl_professor")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pessoa_id", unique = true, nullable = false)
	private Pessoa pessoa;

	protected Professor() {
	}

	// TODO: falta hashCode() e equals

	@Builder
	public Professor(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
