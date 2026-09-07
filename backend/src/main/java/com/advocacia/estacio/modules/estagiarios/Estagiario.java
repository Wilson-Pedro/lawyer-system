package com.advocacia.estacio.modules.estagiarios;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Table(name = "tbl_estagiario")
public class Estagiario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pessoa_id", unique = true, nullable = false)
    private Pessoa pessoa;

    @Column(nullable = false, unique = true, length = 50)
    private String matricula;

    @Enumerated(EnumType.STRING)
    private PeriodoEstagio periodo;

    protected Estagiario() {
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matricula);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Estagiario outro)) return false;
        return matricula != null && Objects.equals(matricula, outro.getMatricula());
    }

    @Builder
    private Estagiario(String matricula, Pessoa pessoa, PeriodoEstagio periodo) {
        this.matricula = matricula;
        this.pessoa = pessoa;
        this.periodo = periodo;
    }

    public void promoverParaPeriodo(PeriodoEstagio novoPeriodo) {
        if (novoPeriodo != null) {
            this.periodo = novoPeriodo;
        }
    }
}
