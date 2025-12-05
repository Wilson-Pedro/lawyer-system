package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.Ator;
import org.springframework.data.domain.Page;

public interface AtorService {

	Ator salvar(AtorDto atorDto);

	Page<Ator> buscarTodosPorTipoDoAtor(String tipoDoAtor, int page, int size);
}
