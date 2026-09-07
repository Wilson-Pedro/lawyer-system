package com.advocacia.estacio.modules.auth;

import com.advocacia.estacio.modules.usuarios.enums.UsuarioRole;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public interface AuthDTO {
    record LoginRequest(
            @NotBlank  String login,
            @NotBlank String password
    ) {}

    record LoginResponse(
            String token,
            String tipo,
            Long id,
            String login,
            UsuarioRole role,
            Instant expiraEm
    ) {
        public LoginResponse(String token, String tipo, Long id, String login,
                             UsuarioRole role, Instant expiraEm) {
            this.token = token;
            this.tipo = tipo;
            this.id = id;
            this.login = login;
            this.role = role;
            this.expiraEm = expiraEm;
        }
    }

    record ResetPasswordRequest(
            @NotBlank String login,
            @NotBlank String novaSenha
    ) {}
}
