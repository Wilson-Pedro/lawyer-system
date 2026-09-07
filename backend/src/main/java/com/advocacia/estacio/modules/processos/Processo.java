package com.advocacia.estacio.modules.processos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tbl_processo")
@Getter
public class Processo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    TODO: verificar se processos pertencem a demandas
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "demanda_id", nullable = false, unique = true)
//    private Demanda demanda;


    @Column(name = "numero_processo", unique = true, nullable = false, length = 50)
    private String numeroDoProcesso;

    @Column(name = "numero_processo_pje", unique = true, length = 50)
    private String numeroDoProcessoPje;

    @Enumerated(EnumType.STRING)
    private AreaDoDireito areaDoDireito;

    @Enumerated(EnumType.STRING)
    private Tribunal tribunal;

    private String assunto;

    // TODO: talvez utilizar um tbl no banco devido as constantes alterações e
    // ao grande número de varas
    @Column(length = 100)
    private String vara;

    @Column(length = 100)
    private String responsavel;

    @Enumerated(EnumType.STRING)
    private ProcessoStatus statusDoProcesso;

    @Column(name = "prazo_final")
    private LocalDate prazoFinal;

    @Column(name = "partes_envolvidas")
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcessoParte> partesEnvolvidas = new ArrayList<>();

    @Column(name = "criacao")
    @CreationTimestamp
    private LocalDateTime criacao;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    protected Processo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Processo processo)) return false;
        return numeroDoProcesso != null && Objects.equals(numeroDoProcesso, processo.getNumeroDoProcesso());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroDoProcesso);
    }

    @Builder
    public Processo(String numeroDoProcesso, String numeroDoProcessoPje, AreaDoDireito areaDoDireito,
                    Tribunal tribunal, String assunto, String vara, String responsavel,
                    ProcessoStatus statusDoProcesso, LocalDate prazoFinal) {
        this.numeroDoProcesso = numeroDoProcesso;
        this.numeroDoProcessoPje = numeroDoProcessoPje;
        this.areaDoDireito = areaDoDireito;
        this.tribunal = tribunal;
        this.assunto = assunto;
        this.vara = vara;
        this.responsavel = responsavel;
        this.statusDoProcesso = statusDoProcesso;
        this.prazoFinal = prazoFinal;
    }

    public void adicionarParteEnvolvida(ProcessoParte parteEnvolvida) {
        this.partesEnvolvidas.add(parteEnvolvida);
    }
}
