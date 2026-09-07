package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.demandas.movimentacoes.DemandaMovimentacao;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.demandas.EtapaDemanda;
import com.advocacia.estacio.modules.demandas.Tempestividade;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_demanda")
@Getter
public class Demanda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advogado_id")
    private Advogado advogado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estagiario_id")
    private Estagiario estagiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(name = "descricao_demanda", nullable = false, columnDefinition = "TEXT")
    private String descricaoDemanda;

    private LocalDate prazo;

    @Column(name = "prazo_documentos")
    private LocalDate prazoDocumentos;

    // para evitar buscas ao banco
    @Enumerated(EnumType.STRING)
    private EtapaDemanda statusAtual = EtapaDemanda.AGUARDANDO_ALUNO;

    @OneToMany(mappedBy = "demanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandaMovimentacao> movimentacoes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Tempestividade tempestividade = Tempestividade.DENTRO_DO_PRAZO;

    @Column(name = "data_abertura", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataAbertura;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    protected Demanda() {
    }

    // TODO: hashCode() e equals()

    @Builder
    public Demanda(Advogado advogado, Estagiario estagiario, Professor professor,
                   String descricaoDemanda, LocalDate prazo, LocalDate prazoDocumentos,
                   EtapaDemanda statusAtual, List<DemandaMovimentacao> movimentacoes,
                   Tempestividade tempestividade, LocalDateTime dataAbertura,
                   LocalDateTime ultimaAtualizacao) {

        this.advogado = advogado;
        this.estagiario = estagiario;
        this.professor = professor;
        this.descricaoDemanda = descricaoDemanda;
        this.prazo = prazo;
        this.statusAtual = statusAtual;
        this.prazoDocumentos = prazoDocumentos;
        this.movimentacoes = movimentacoes;
        this.tempestividade = tempestividade;
        this.dataAbertura = dataAbertura;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    // ===============================================
    // MÉTODOS AUXILIARES
    // ===============================================

    /**
     * Adiciona uma movimentação à Demanda e sincroniza o estado de ambos.
     */
    public void adicionarMovimentacao(DemandaMovimentacao movimentacao) {
        this.movimentacoes.add(movimentacao);
        movimentacao.setDemanda(this);
        this.statusAtual = movimentacao.getEtapa();
        this.ultimaAtualizacao = LocalDateTime.now();
    }


}
