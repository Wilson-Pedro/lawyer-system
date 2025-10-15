package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;

public interface EstagiarioService {

	Estagiario salvar(EstagiarioDto estagiarioDto);
	
	Estagiario buscarPorId(Long id);
}
