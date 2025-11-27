package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.services.DemandaService;
import com.advocacia.estacio.services.EstagiarioService;

@Service
public class DemandaServiceImpl implements DemandaService {
	
	@Autowired
	private DemandaRepository demandaRepository;
	
	@Autowired
	private EstagiarioService estagiarioService;

	@Override
	public Demanda salvar(DemandaDto damandaDto) {
		Estagiario estagiario = estagiarioService.buscarPorId(damandaDto.getEstagiarioId());
		Demanda demanda = new Demanda(damandaDto);
		demanda.setEstagiario(estagiario);
		return demandaRepository.save(demanda);
	}
	
	@Override
	public Page<DemandaDto> buscarTodos(int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRepository.buscarTodos(pageable);
	}
	
	@Override
	public Page<DemandaDto> buscarTodosPorEstagiarioId(Long estagiarioId, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRepository.buscarTodosPorEstagiarioId(estagiarioId, pageable);
	}
	
	@Override
	public Page<DemandaDto> buscarTodosPorStatus(String demandaStatus, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRepository.buscarTodosPorStatus(DemandaStatus.toEnum(demandaStatus), pageable);
	}
}
