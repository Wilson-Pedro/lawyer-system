package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DemandaStatusDto;
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
	
	Page<DemandaDto> buscarTodosPorUserId(Long estagiarioId, int page, int size);

	Page<DemandaDto> buscarTodosPorProfessorId(Long professorId, int page, int size);

	Page<DemandaDto> buscarTodosPorAdvogadoId(Long advogadoId, int page, int size);
	
	Page<DemandaDto> buscarTodosPorStatus(String demandaStatus, int page, int size);

	//void mudarDemandaStatus(Long id, String status);

	void mudarDemandaStatus(Long id, DemandaStatusDto demandaStatusDto);

	List<DemandaStatus> getDemandaStatus(UserRole role);
}
