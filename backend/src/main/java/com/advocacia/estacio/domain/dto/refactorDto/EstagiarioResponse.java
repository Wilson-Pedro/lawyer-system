package com.advocacia.estacio.domain.dto.refactorDto;

import com.advocacia.estacio.domain.entities.Estagiario;

public record EstagiarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String matricula,
        String periodo
) {
    public EstagiarioResponse(Estagiario estagiario) {
        this(
                estagiario.getId(),
                estagiario.getNome(),
                estagiario.getEmail(),
                estagiario.getTelefone(),
                estagiario.getMatricula(),
                estagiario.getPeriodo().getDescricao()
        );
    }
}