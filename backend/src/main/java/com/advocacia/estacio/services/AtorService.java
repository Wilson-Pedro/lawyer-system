package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;

public interface AtorService {

	Ator salvar(AtorDto atorDto);
	
	Ator buscarPorId(Long id);
	
	Ator atualizar(Long id, AtorDto atorDto);

	Page<Ator> buscarTodosPorTipoDoAtor(String tipoDoAtor, int page, int size);
}
