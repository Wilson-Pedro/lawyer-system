package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.domain.enums.UserRole;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;

import java.util.List;

public interface DemandaService {
	
	Demanda salvar(DemandaDto damandaDto);

	Demanda buscarPorId(Long id);
	
	Page<DemandaDto> buscarTodos(int page, int size);
	
	Page<DemandaDto> buscarTodosPorEstagiarioId(Long estagiarioId, int page, int size);

	//Page<DemandaDto> buscarTodosPorAdvogadooId(Long advogadoId, int page, int size);
	
	Page<DemandaDto> buscarTodosPorStatus(String demandaStatus, int page, int size);

	void mudarDemandaStatus(Long id, String status);

	List<DemandaStatus> getDemandaStatus(UserRole role);
}
