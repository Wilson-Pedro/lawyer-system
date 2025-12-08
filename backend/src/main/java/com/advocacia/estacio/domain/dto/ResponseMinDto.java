package com.advocacia.estacio.domain.dto;

import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.utils.Utils;

import java.time.LocalDateTime;

public class ResponseMinDto {

    private Long id;

    private String nome;

    private String email;

    private String registro;

    public ResponseMinDto(Long id, String nome, String email, LocalDateTime registro) {
        this.email = email;
        this.id = id;
        this.nome = nome;
        this.registro = Utils.localDateTimeToString(registro);
    }

    public ResponseMinDto(Ator ator) {
        this.id = ator.getId();
        this.nome = ator.getNome();
        this.email = ator.getEmail();
        this.registro = Utils.localDateTimeToString(ator.getRegistro());
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRegistro() {
        return registro;
    }
}
