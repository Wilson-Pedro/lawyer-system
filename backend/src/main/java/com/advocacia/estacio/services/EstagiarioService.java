package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;

public interface EstagiarioService {

	Estagiario salvar(EstagiarioDto estagiarioDto);
	
	Estagiario buscarPorId(Long id);
	
	Page<Estagiario> buscarEstagiario(String nome, int page, int size);
}
