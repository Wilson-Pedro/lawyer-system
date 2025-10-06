package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;

public interface AdvogadoService {

	Advogado salvar(AdvogadoDto advogadoDto);
	
	Advogado buscarPorId(Long id);
	
	Page<Advogado> buscarAdvogado(String nome, int page, int size);
}
