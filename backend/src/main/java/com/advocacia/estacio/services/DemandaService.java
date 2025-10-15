package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;

public interface DemandaService {
	
	Demanda salvar(DemandaDto damandaDto);
}
