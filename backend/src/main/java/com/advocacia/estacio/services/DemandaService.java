package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;

public interface DemandaService {
	
	Demanda salvar(DemandaDto damandaDto);
	
	Page<DemandaDto> buscarTodos(int page, int size);
	
	Page<DemandaDto> buscarTodosPorEstagiarioId(Long estagiarioId, int page, int size);
}
