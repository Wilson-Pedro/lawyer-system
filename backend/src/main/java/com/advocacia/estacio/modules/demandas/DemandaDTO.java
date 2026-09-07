package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.modules.demandas.Demanda;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DemandaDTO {
    @Schema(name = "DemandaCreateRequest")
    record Request(
//            @Schema(description = "Área do direito correspondente", example = "CIVIL")
            Long advogadoId,
            Long estagiarioId,
            Long professorId,
            @Schema(example = "Cliente sofreu acidente de trânsito e a seguradora se recusa a pagar.")
            @NotBlank String descricaoDemanda,
            Integer diasAdicionais,
            LocalDate prazoDocumentos
    ) {}

    @Schema(name = "DemandaResponse")
    record Response(
            Long id,
            String descricaoDemanda,
            LocalDate prazo,
            LocalDateTime dataAbertura
    ) {
        public Response(Demanda demanda) {
            this(
                    demanda.getId(),
                    demanda.getDescricaoDemanda(),
                    demanda.getPrazo(),
                    demanda.getDataAbertura()
            );
        }
    }

    @Schema(name = "DemandaListResponse")
    record ListResponse(
            Long id,
            String descricaoDemanda,
            LocalDate prazo,
            String nomeEstagiario,
            String nomeProfessor
    ) {
        public ListResponse(Demanda demanda) {
            this( demanda.getId(),
                    demanda.getDescricaoDemanda(),
                    demanda.getPrazo(),
                    demanda.getEstagiario().getPessoa().getNome(),
                    demanda.getProfessor().getPessoa().getNome()
            );
        }
    }
}
