package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;

import java.time.LocalDate;
import java.util.List;

public interface UsuarioAuthService {

	UsuarioAuth salvar(RegistroDto dto);

	LoginResponseDto login(AuthenticationDto dto);

	void atualizarLogin(String loginAntigo, String loginNovo, String senha, UsuarioStatus usuarioStatus);

	List<UsuarioStatus> getUsuarioStatus();

	void ativarUsuarios(List<UsuarioAuth> usuarioAuths);

	void desativarAtivarUsuarios(List<UsuarioAuth> usuarioAuths, UsuarioStatus usuarioStatus);

	void definirDataParaAtivarDesativar(Long id, String data);

	void desativarAtivarUsuariosPorData(LocalDate dataParaDesativar, List<UsuarioAuth> usuarioAuths, UsuarioStatus usuarioStatus);

	DesativarAtivarUsuarioPorData buscarDesativarUsuarioPorId(Long id);

	List<UsuarioAuth> buscarUsuariosAuthPorRole(UserRole userRole);
}
