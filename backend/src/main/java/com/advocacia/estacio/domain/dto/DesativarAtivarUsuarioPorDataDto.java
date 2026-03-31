package com.advocacia.estacio.domain.dto;

import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.utils.Utils;

public class DesativarAtivarUsuarioPorDataDto {

    private Long id;

    private String tipoUsuario;

    private String dataDeDesativacao;

    private UsuarioStatus usuarioStatus;

    public DesativarAtivarUsuarioPorDataDto() {
    }

    public DesativarAtivarUsuarioPorDataDto(String tipoUsuario, String dataDeDesativacao, UsuarioStatus usuarioStatus) {
        this.tipoUsuario = tipoUsuario;
        this.dataDeDesativacao = dataDeDesativacao;
        this.usuarioStatus = usuarioStatus;
    }

    public DesativarAtivarUsuarioPorDataDto(DesativarAtivarUsuarioPorData entity) {
        this.tipoUsuario = entity.getTipoUsuario().getRole();
        this.dataDeDesativacao = Utils.stringToLocalDate(entity.getDataDeDesativacao());
        this.usuarioStatus = entity.getUsuarioStatus();
    }

    public Long getId() {
        return id;
    }

    public String getDataDeDesativacao() {
        return dataDeDesativacao;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public UsuarioStatus getUsuarioStatus() {
        return usuarioStatus;
    }
}
