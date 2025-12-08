package com.advocacia.estacio.services.impl;


import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.DemandaRespondeRepository;
import com.advocacia.estacio.services.DemandaRespondeService;
import com.advocacia.estacio.services.DemandaService;
import com.advocacia.estacio.services.EstagiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DemandaRespondeServiceImpl implements DemandaRespondeService {
	
	@Autowired
	private DemandaRespondeRepository demandaRespondeRepository;
	
	@Autowired
	private EstagiarioService estagiarioService;

	@Autowired
	private DemandaService demandaService;

	@Override
	public DemandaResponde salvar(DemandaRespondeDto dto) {
		Demanda demanda = demandaService.buscarPorId(dto.getDemandaId());
		Estagiario estagiario = estagiarioService.buscarPorId(dto.getEstagiarioId());
		DemandaResponde demandaResponde = new DemandaResponde(dto);
		demandaResponde.setDemanda(demanda);
		demandaResponde.setEstagiario(estagiario);
		return demandaRespondeRepository.save(demandaResponde);
	}

	@Override
	public Page<DemandaRespondeDto> buscarDemandasRespostasPorDemandaId(Long demandaId, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRespondeRepository.buscarDemandasRespostasPorDemandaId(demandaId, pageable);
	}
}
