package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.entities.Movimento;
import com.advocacia.estacio.domain.entities.Processo;

public interface MovimentoService {

	Movimento salvar(MovimentoDto MovimentoDto);
	
	Page<Movimento> buscarMovimentosPorProcesso(String numeroDoProcesso, int page, int size);
}
