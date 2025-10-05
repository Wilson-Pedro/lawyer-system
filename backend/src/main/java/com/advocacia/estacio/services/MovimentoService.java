package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.entities.Movimento;

public interface MovimentoService {

	Movimento salvar(MovimentoDto MovimentoDto);
}
