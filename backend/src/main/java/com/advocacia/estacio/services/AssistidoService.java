package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;

public interface AssistidoService {

	Assistido salvar(AssistidoDto assistidoDto);
	
	Assistido buscarPorId(Long assistidoId);
	
	Page<Assistido> buscarAssistido(String nome, int page, int size);
}
