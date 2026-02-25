package com.advocacia.estacio.domain.dto;

public class DataParaDesativarUsuariosDto {

    private String tipoUsuario;

    private String dataDeDesativacao;

    public DataParaDesativarUsuariosDto() {
    }

    public DataParaDesativarUsuariosDto(String tipoUsuario, String dataDeDesativacao) {
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
