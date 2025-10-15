package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.Estagiario;
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
}
