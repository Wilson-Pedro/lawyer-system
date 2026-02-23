package com.advocacia.estacio.domain.dto;

public class DesativarUsuarioDto {

    private String tipoUsuario;

    private String dataDeDesativacao;

    public DesativarUsuarioDto(String tipoUsuario, String dataDeDesativacao) {
        this.tipoUsuario = tipoUsuario;
        this.dataDeDesativacao = dataDeDesativacao;
    }

    public String getDataDeDesativacao() {
        return dataDeDesativacao;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
