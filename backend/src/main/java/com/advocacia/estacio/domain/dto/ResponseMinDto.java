package com.advocacia.estacio.domain.dto;

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

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getNome(String robertoCarlos) {
        return nome;
    }

    public String getRegistro() {
        return registro;
    }
}
