package com.advocacia.estacio.domain.entities;
import com.advocacia.estacio.domain.dto.DataParaDesativarUsuariosDto;
import com.advocacia.estacio.domain.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDate;

import static com.advocacia.estacio.utils.Utils.localDateToString;

@Entity
@Table(name = "TBL_DESATIVAR_USUARIO")
public class DataParaDesativarUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserRole tipoUsuario;

    @Column(unique = true)
    private LocalDate dataDeDesativacao;

    public DataParaDesativarUsuarios() {
    }

    public DataParaDesativarUsuarios(UserRole tipoUsuario, LocalDate dataDeDesativacao) {
        this.tipoUsuario = tipoUsuario;
        this.dataDeDesativacao = dataDeDesativacao;
    }

    public DataParaDesativarUsuarios(DataParaDesativarUsuariosDto dto) {
        this.tipoUsuario = UserRole.toEnum(dto.getTipoUsuario());
        this.dataDeDesativacao = localDateToString(dto.getDataDeDesativacao());
    }

    public LocalDate getDataDeDesativacao() {
        return dataDeDesativacao;
    }

    public Long getId() {
        return id;
    }

    public UserRole getTipoUsuario() {
        return tipoUsuario;
    }

    public void setDataDeDesativacao(LocalDate dataDeDesativacao) {
        this.dataDeDesativacao = dataDeDesativacao;
    }
}
