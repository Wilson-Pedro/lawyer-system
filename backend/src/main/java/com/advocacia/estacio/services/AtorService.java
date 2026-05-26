package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.RequestIds;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.TipoDoAtor;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;

import java.util.List;

public interface AtorService {

	Ator salvar(AtorDto atorDto);
	
	Ator buscarPorId(Long id);
	
	Ator atualizar(Long id, AtorDto atorDto);

	Ator buscarIdPorEmail(String email);

	Page<Ator> buscarTodosPorTipoDoAtor(String tipoDoAtor, int page, int size);

	List<TipoDoAtor> getTipoAtores();

	List<UsuarioAuth> buscarUsuariosAuthPorId(List<Long> ids);

	void desativarAtores(RequestIds requestIds);

	Page<Ator> buscarAtor(String nome, String tipoDoAtor, int page, int size);
}
