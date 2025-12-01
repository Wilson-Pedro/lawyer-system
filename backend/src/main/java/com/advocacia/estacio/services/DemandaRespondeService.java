package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.entities.DemandaResponde;

public interface DemandaRespondeService {

	DemandaResponde salvar(DemandaRespondeDto dto);
}
