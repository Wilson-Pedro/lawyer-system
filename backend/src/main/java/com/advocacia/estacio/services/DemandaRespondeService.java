package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import org.springframework.data.domain.Page;

public interface DemandaRespondeService {

	DemandaResponde salvar(DemandaRespondeDto dto);

	Page<DemandaRespondeDto> buscarDemandasRespostasPorDemandaId(Long demandaId, int page, int size);
}
