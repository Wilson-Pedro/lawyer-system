package com.advocacia.estacio.domain.entities;
import com.advocacia.estacio.domain.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_DESATIVAR_USUARIO")
public class DataParaDesativarUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserRole tipoUsuario;

    @Column(unique = true)
    private LocalDate dataDeDesativacao;

    public DataParaDesativarUsuario() {
    }

    public DataParaDesativarUsuario(UserRole tipoUsuario, LocalDate dataDeDesativacao) {
        this.tipoUsuario = tipoUsuario;
        this.dataDeDesativacao = dataDeDesativacao;
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
