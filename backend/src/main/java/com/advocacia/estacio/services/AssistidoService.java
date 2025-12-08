package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;

public interface AssistidoService {

	Assistido salvar(AssistidoDto assistidoDto);
	
	Assistido buscarPorId(Long id);
	
	Assistido atualizar(Long id, AssistidoDto assistidoDto);
	
	Page<Assistido> buscarAssistidoPorNome(String nome, int page, int size);

	Page<ResponseMinDto> buscarTodos(int page, int size);
}
