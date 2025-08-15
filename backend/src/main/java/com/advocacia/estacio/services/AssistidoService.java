package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;

public interface AssistidoService {

	Assistido salvar(AssistidoDto assistidoDto);
}
