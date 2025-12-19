package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.records.EntidadeMinDto;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.Advogado;

public interface AdvogadoService {

	Advogado salvar(AdvogadoDto advogadoDto);
	
	Advogado buscarPorId(Long id);
	
	Advogado atualizar(Long id, AdvogadoDto advogadoDto);

	Advogado buscarIdPorEmail(String email);
	
	Page<Advogado> buscarAdvogado(String nome, int page, int size);

	Page<ResponseMinDto> buscarTodos(int page, int size);
}
