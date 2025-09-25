package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;

public interface AdvogadoService {

	public Advogado salvar(AdvogadoDto advogadoDto);
}
