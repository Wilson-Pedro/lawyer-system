package com.advocacia.estacio.services;

import java.util.List;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;

public interface UsuarioAuthService {

	UsuarioAuth salvar(RegistroDto dto);

	LoginResponseDto login(AuthenticationDto dto);

	void atualizarLogin(String loginAntigo, String loginNovo, String senha, UsuarioStatus usuarioStatus);

	List<UsuarioStatus> getUsuarioStatus();

	void ativarUsuarios(List<UsuarioAuth> usuarioAuths);

	void desativarAtivarUsuarios(List<UsuarioAuth> usuarioAuths, UsuarioStatus usuarioStatus);

	//void definirDataParaAtivarDesativar(Long id, String data);

	void desativarAtivarUsuariosPorData();

	void definirDataDeDesativacao(DesativarAtivarUsuarioPorDataDto dto);

	DesativarAtivarUsuarioPorData buscarDesativarUsuarioPorId(Long id);

	List<UsuarioAuth> buscarUsuariosAuthPorRole(UserRole userRole);
}
