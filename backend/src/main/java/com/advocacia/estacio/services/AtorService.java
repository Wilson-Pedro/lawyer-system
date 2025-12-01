package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;

public interface AtorService {

	Ator salvar(AtorDto atorDto);
}
