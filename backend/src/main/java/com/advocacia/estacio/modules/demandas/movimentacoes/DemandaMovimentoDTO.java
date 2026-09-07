package com.advocacia.estacio.modules.demandas.movimentacoes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public interface DemandaMovimentoDTO {
    record Request(
            @NotNull Long demandaId,
            @NotNull Long avaliadorId,
            @NotBlank String statusAvaliacao,
            String observacoes
    ) {}

    record Response(
            Long id,
            String statusAvaliacao,
            String observacoes,
            String nomeAvaliador,
            LocalDateTime createdAt
    ) {}
}
