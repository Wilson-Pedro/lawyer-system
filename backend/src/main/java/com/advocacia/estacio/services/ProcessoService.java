package com.advocacia.estacio.services;

import java.util.List;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;

public interface ProcessoService {
	
	Processo salvar(ProcessoRequestDto request);
	
	List<ProcessoDto> buscarProcessosPorStatusDoProcesso();
}
