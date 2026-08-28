package com.advocacia.estacio.domain.dto.refactorDto;

import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import jakarta.validation.constraints.NotBlank;

public record EstagiarioRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        String email,
        String telefone,
        @NotBlank(message = "A matrícula é obrigatória")
        String matricula,
        PeriodoEstagio periodo,
        //TODO: a senha será automatizada e enviada por email
        @NotBlank(message = "A senha é obrigatória")
        String senha
) { }

