package com.advocacia.estacio.modules.demandas.movimentacoes;

import com.advocacia.estacio.modules.demandas.Demanda;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.demandas.EtapaDemanda;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_demanda_movimentacao")
public class DemandaMovimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "demanda_id", nullable = false)
    private Demanda demanda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Pessoa autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa", nullable = false, length = 50)
    private EtapaDemanda etapa;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "criacao", updatable = false)
    private LocalDateTime criacao;

    protected DemandaMovimentacao() {
    }

    //TODO: hashCode() e equals()

    @Builder
    public DemandaMovimentacao(String observacoes, EtapaDemanda etapa, Pessoa autor, Demanda demanda) {
        this.observacoes = observacoes;
        this.etapa = etapa;
        this.autor = autor;
        this.demanda = demanda;
    }
}