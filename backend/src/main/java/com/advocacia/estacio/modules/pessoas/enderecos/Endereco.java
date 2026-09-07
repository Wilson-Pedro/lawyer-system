package com.advocacia.estacio.modules.pessoas.enderecos;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Getter
@Table(name = "tbl_endereco")
public class Endereco implements Serializable {
//    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String cidade;

    @Column(length = 100)
    private String bairro;

    @Column(nullable = false)
    private String rua;

    @Column(name = "numero_da_casa", length = 10)
    private String numeroDaCasa;

    @Column(length = 20)
    private String cep;

    @Column(name = "criacao", updatable = false)
    @CreationTimestamp
    private LocalDateTime criacao;

    protected Endereco() {
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof Endereco outro)) return false;
        return id != null && id.equals(outro.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Builder
    public Endereco(String cidade, String bairro, String rua, String numeroDaCasa, String cep) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroDaCasa = numeroDaCasa;
        this.cep = cep;
    }
}
