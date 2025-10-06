package com.advocacia.estacio.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;

public interface ProcessoService {
	
	Processo salvar(ProcessoRequestDto request);
	
	Processo buscarPorId(Long id);
	
	Processo buscarPorNumeroDoProcesso(String numeroDoProcesso);
	
	List<ProcessoDto> buscarProcessosPorStatusDoProcesso();
	
	Page<Processo> buscarProcesso(String numeroDoProcesso, int page, int size);
}
