package com.advocacia.estacio.domain.dto.refactorDto;

import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;

import java.time.LocalDateTime;

/**
 *  DTO exclusivo para listagem de estagiários.
 * */
public record EstagiarioListResponse(
        Long id,
        String nome,
        String matricula,
        String telefone,
        String email,
        PeriodoEstagio periodo,
        LocalDateTime registro
) {
    public EstagiarioListResponse(Estagiario estagiario) {
        this(
                estagiario.getId(),
                estagiario.getNome(),
                estagiario.getMatricula(),
                estagiario.getTelefone(),
                estagiario.getEmail(),
                estagiario.getPeriodo(),
                estagiario.getRegistro()
        );
    }
}
