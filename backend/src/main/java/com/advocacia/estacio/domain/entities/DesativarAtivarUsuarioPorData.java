package com.advocacia.estacio.domain.entities;
import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.utils.Utils;
import jakarta.persistence.*;

import java.time.LocalDate;

import static com.advocacia.estacio.utils.Utils.stringToLocalDate;

@Entity
@Table(name = "TBL_DESATIVAR_USUARIO")
public class DesativarAtivarUsuarioPorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserRole tipoUsuario;

    @Column(nullable = true)
    private LocalDate dataDeDesativacao;

    private UsuarioStatus usuarioStatus;

    public DesativarAtivarUsuarioPorData() {
    }

    public DesativarAtivarUsuarioPorData(UserRole tipoUsuario, LocalDate dataDeDesativacao, UsuarioStatus usuarioStatus) {
        this.tipoUsuario = tipoUsuario;
        this.dataDeDesativacao = dataDeDesativacao;
        this.usuarioStatus = usuarioStatus;
    }

    public DesativarAtivarUsuarioPorData(DesativarAtivarUsuarioPorDataDto dto) {
        this.tipoUsuario = UserRole.toEnum(dto.getTipoUsuario());
        this.dataDeDesativacao = Utils.stringToLocalDate(dto.getDataDeDesativacao());
        this.usuarioStatus = dto.getUsuarioStatus();
    }

    public LocalDate getDataDeDesativacao() {
        return dataDeDesativacao;
    }

    public void setDataDeDesativacao(LocalDate dataDeDesativacao) {
        this.dataDeDesativacao = dataDeDesativacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(UserRole tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public UsuarioStatus getUsuarioStatus() {
        return usuarioStatus;
    }

    public void setUsuarioStatus(UsuarioStatus usuarioStatus) {
        this.usuarioStatus = usuarioStatus;
    }
}
