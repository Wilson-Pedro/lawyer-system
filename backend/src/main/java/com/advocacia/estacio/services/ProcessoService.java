package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;

public interface ProcessoService {
	
	Processo salvar(ProcessoRequestDto request);
}
