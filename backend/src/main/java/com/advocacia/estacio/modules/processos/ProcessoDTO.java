package com.advocacia.estacio.modules.processos;

import com.advocacia.estacio.modules.estagiarios.EstagiarioDTO;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public interface ProcessoDTO {
    record Request(
            Long advogadoId,
            Long estagiarioId,
            Long assistidoId,
            @NotBlank String numeroProcesso,
            String numeroProcessoPje,
            String areaDoDireito,
            String assunto,
            String vara,
            String tribunal,
            String statusDoProcesso,
            LocalDate prazoFinal
    ) {}

    record Response(
            Long id,
            String numeroProcesso,
            String numeroProcessoPje,
            String areaDoDireito,
            String assunto,
            String statusDoProcesso,
            LocalDate prazoFinal,
            EstagiarioDTO.ListResponse estagiarioResponsavel
    ){}

    record ListResponse(
            Long id,
            String numeroProcesso,
            String statusDoProcesso,
            String nomeAssistido,
            LocalDate prazoFinal
    ) {}
}
