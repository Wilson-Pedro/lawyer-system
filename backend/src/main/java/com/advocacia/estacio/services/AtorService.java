package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.enums.TipoDoAtor;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;

import java.util.List;

public interface AtorService {

	Ator salvar(AtorDto atorDto);
	
	Ator buscarPorId(Long id);
	
	Ator atualizar(Long id, AtorDto atorDto);

	Page<Ator> buscarTodosPorTipoDoAtor(String tipoDoAtor, int page, int size);

	List<TipoDoAtor> getTipoAtores();
}
