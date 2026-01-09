package com.advocacia.estacio.services;

import java.util.List;

import com.advocacia.estacio.domain.enums.AreaDoDireito;
import com.advocacia.estacio.domain.enums.StatusProcesso;
import com.advocacia.estacio.domain.enums.Tribunal;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.dto.ProcessoUpdate;
import com.advocacia.estacio.domain.entities.Processo;

public interface ProcessoService {
	
	Processo salvar(ProcessoRequestDto request);
	
	Processo buscarPorId(Long id);
	
	Processo buscarPorNumeroDoProcesso(String numeroDoProcesso);
	
	List<ProcessoDto> buscarProcessosPorStatusDoProcesso(String processoStatus);
	
	List<Processo> findAll();
	
	Page<Processo> buscarProcesso(String numeroDoProcesso, int page, int size);
	
	Processo atualizarProcesso(Long id, ProcessoUpdate processoUpdate);
	
	void validarProcesso(Processo processo);

	List<AreaDoDireito> getAreasDoDireito();

	List<Tribunal> getTribunais();

	List<StatusProcesso> getProcessoStatus();
}
