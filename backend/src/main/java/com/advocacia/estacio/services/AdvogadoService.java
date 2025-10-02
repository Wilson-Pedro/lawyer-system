package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;

public interface AdvogadoService {

	Advogado salvar(AdvogadoDto advogadoDto);
	
	Advogado findById(Long id);
}
