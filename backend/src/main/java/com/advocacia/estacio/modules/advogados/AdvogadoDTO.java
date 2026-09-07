package com.advocacia.estacio.modules.advogados;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoDTO;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.enums.UsuarioStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AdvogadoDTO {
    @Schema(name = "AdvogadoCreateRequest")
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @Email String email,
            String senha,
            @Pattern(regexp = "^\\d{9,11}$", message = "Telefone deve ter entre 9 e 11 dígitos numéricos")
            String telefone,
            LocalDate dataNascimento,
            @Valid EnderecoDTO.Request endereco
    ) {
        public Advogado toEntity(Usuario usuario, Endereco endereco ) {
            Pessoa pessoa = Pessoa.builder()
                    .nome(this.nome())
                    .email(this.email())
                    .telefone(this.telefone())
                    .dataNascimento(this.dataNascimento())
                    .endereco(endereco)
                    .build();

            pessoa.vincularUsuario(usuario);

            return Advogado.builder()
                    .pessoa(pessoa)
                    .build();
        }
    }

    record Response(
            Long id,
            String nome,
            String email,
            String telefone,
            LocalDate DataNascimento,
            EnderecoDTO.Response endereco,
            String usuarioStatus,
            LocalDateTime criadoEm,
            LocalDateTime desativadoEm
    ) {
        public Response (Advogado advogado) {
            this(
                    advogado.getId(),
                    advogado.getPessoa().getNome(),
                    advogado.getPessoa().getEmail(),
                    advogado.getPessoa().getTelefone(),
                    advogado.getPessoa().getDataNascimento(),
                    EnderecoDTO.Response.from(advogado.getPessoa().getEndereco()),
                    UsuarioStatus.obterDescricao(advogado.getPessoa().getUsuario().getStatus()),
                    advogado.getPessoa().getCriadoEm(),
                    advogado.getPessoa().getUsuario().getDesativadoEm()
            );
        }
    }

    record ListResponse(
            Long id,
            String nome,
            String email,
            String telefone,
            String status
    ) {
        public ListResponse(Advogado advogado) {
            this(
                    advogado.getId(),
                    advogado.getPessoa().getNome(),
                    advogado.getPessoa().getEmail(),
                    advogado.getPessoa().getTelefone(),
                    UsuarioStatus.obterDescricao(advogado.getPessoa().getUsuario().getStatus())
            );
        }
    }

    record Filter(
            String nome,
            UsuarioStatus status){}

    record AutocompleteResponse(
            Long id,
            String nome
    ) {
        public AutocompleteResponse(Advogado advogado) {
            this(
                    advogado.getId(),
                    advogado.getPessoa().getNome()
            );
        }
    }

    record UpdateRequest(
            String nome,
            // String email,
            @Pattern(regexp = "^\\d{9,11}$") String telefone,
            // TODO: n aceitar datas irreais
            LocalDate dataNascimento,
            @Valid EnderecoDTO.Request endereco
    ) {}
}
