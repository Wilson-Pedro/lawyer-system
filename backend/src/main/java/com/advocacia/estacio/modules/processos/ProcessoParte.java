package com.advocacia.estacio.modules.processos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "tbl_processo_parte")
@Getter
public class ProcessoParte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo_participacao", nullable = false, length = 50)
    private TipoParte tipoParticipacao;

    protected ProcessoParte() {
    }

    @Builder
    public ProcessoParte(Processo processo, String nome, TipoParte tipoParticipacao) {
        this.processo = processo;
        this.nome = nome;
        this.tipoParticipacao = tipoParticipacao;
    }

    // TODO: hashCode() e equals()
}