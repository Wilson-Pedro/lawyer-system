package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.dto.RequestIds;
import com.advocacia.estacio.domain.dto.refactorDto.EstagiarioListResponse;
import com.advocacia.estacio.domain.dto.refactorDto.EstagiarioRequest;
import com.advocacia.estacio.domain.dto.refactorDto.EstagiarioResponse;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstagiarioService {

	EstagiarioResponse salvar(EstagiarioRequest data);

	Estagiario buscarPorId(Long id);

	EntidadeMinDto buscarIdPorEmail(String email);

	Page<EstagiarioListResponse> buscarTodos(Pageable pageable);
	
	Page<Estagiario> buscarEstagiario(String nome, int page, int size);
	
	Estagiario atualizar(Long id, EstagiarioDto estagiarioDto);

	List<UsuarioAuth> buscarUsuariosAuthPorId(List<Long> ids);

	List<PeriodoEstagio> getPeriodos();

	List<UsuarioAuth> buscarUsuariosAuthPorUsuarioStatus(UsuarioStatus usuarioStatus);

	void desativarEstagiarios(RequestIds requestIds);

	//void desativarEstagiariosPorData(DesativarAtivarUsuarioPorDataDto dto, String usuarioStatus);

	//void definirDataDeDesativacao(Long id, String data);
}
