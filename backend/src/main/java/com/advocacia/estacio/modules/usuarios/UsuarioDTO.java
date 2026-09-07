package com.advocacia.estacio.modules.usuarios;

import com.advocacia.estacio.modules.usuarios.enums.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.enums.UsuarioStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public interface UsuarioDTO {

    // N será possível usar agora devido o jeito atual que os users são criados.
    record CreateRequest(
            @NotBlank String login,
            @NotBlank String password
    ) { }

    record Response(
            Long id,
            String login,
            String role,
            String usuarioStatus,
            LocalDateTime criadoEm,
            LocalDateTime desativadoEm
    ) {
        public Response(Usuario usuario) {
            this(
                    usuario.getId(),
                    usuario.getLogin(),
                    UsuarioRole.obterDescricao(usuario.getRole()),
                    UsuarioStatus.obterDescricao(usuario.getStatus()),
                    usuario.getCriadoEm(),
                    usuario.getDesativadoEm()
            );
        }
    }

    record UpdateRequest(
            String login,
            UsuarioRole role,
            UsuarioStatus status
    ) {}
}
