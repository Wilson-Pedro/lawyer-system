package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;

import java.util.List;

public interface UsuarioAuthService {

	UsuarioAuth salvar(RegistroDto dto);

	LoginResponseDto login(AuthenticationDto dto);

	void atualizarLogin(String loginAntigo, String loginNovo, String senha, UsuarioStatus usuarioStatus);

	List<UsuarioStatus> getUsuarioStatus();
}
