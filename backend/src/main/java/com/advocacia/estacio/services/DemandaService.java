package com.advocacia.estacio.services;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;

public interface DemandaService {
	
	Demanda salvar(DemandaDto damandaDto);

	Demanda buscarPorId(Long id);
	
	Page<DemandaDto> buscarTodos(int page, int size);
	
	Page<DemandaDto> buscarTodosPorEstagiarioId(Long estagiarioId, int page, int size);
	
	Page<DemandaDto> buscarTodosPorStatus(String demandaStatus, int page, int size);
}
