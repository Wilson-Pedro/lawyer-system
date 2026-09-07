package com.advocacia.estacio.modules.assistidos;

import com.advocacia.estacio.modules.pessoas.EstadoCivil;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public interface AssistidoDTO {
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @Email String email,
            @Pattern(regexp = "^\\d{9,11}$", message = "Telefone deve ter entre 9 e 11 dígitos numéricos") String telefone,
            @NotBlank(message = "A matrícula é obrigatória") String matricula,
            String profissao,
            String nacionalidade,
            String naturalidade,
            EstadoCivil estadoCivil,
            @Valid EnderecoDTO.Request endereco
    ) {}

    record Response(
            Long id,
            String matricula,
            String nome,
            String email,
            String telefone,
            String profissao,
            String nacionalidade,
            String naturalidade,
            String estadoCivil,
            EnderecoDTO.Response endereco,
            LocalDateTime criacao
    ) {
        public Response(Assistido assistido) {
            this(
                    assistido.getId(),
                    assistido.getMatricula(),
                    assistido.getPessoa().getNome(),
                    assistido.getPessoa().getEmail(),
                    assistido.getPessoa().getTelefone(),
                    assistido.getProfissao(),
                    assistido.getNacionalidade(),
                    EstadoCivil.obterDescricao(assistido.getEstadoCivil()),
                    assistido.getNaturalidade(),
                    EnderecoDTO.Response.from(assistido.getPessoa().getEndereco()),
                    assistido.getPessoa().getCriadoEm()
            );
        }
    }

    record ListResponse(
            Long id,
            String matricula,
            String nome,
            String email,
            String telefone
    ) {
        public ListResponse (Assistido assistido){
            this(
                    assistido.getId(),
                    assistido.getMatricula(),
                    assistido.getPessoa().getNome(),
                    assistido.getPessoa().getEmail(),
                    assistido.getPessoa().getTelefone()
            );
        }
    }

    record ListResponseFilter(
            String nome,
            String matricula,
            String telefone
    ){}

    record UpdateRequest(
            String nome,
            @Email String email,
            @Pattern(regexp = "^\\d{9,11}$") String telefone,
            String profissao,
            EstadoCivil estadoCivil,
            @Valid EnderecoDTO.Request endereco
    ) {}
}
