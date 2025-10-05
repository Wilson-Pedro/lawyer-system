package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Movimento;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.MovimentoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.MovimentoService;
import com.advocacia.estacio.services.ProcessoService;

@Service
public class MovimentoServiceImpl implements MovimentoService {
	
	@Autowired
	private MovimentoRepository movimentoRepository;
	
	@Autowired
	private AdvogadoService advogadoService;
	
	@Autowired
	private ProcessoService processoService;
	
	
	@Override
	public Movimento salvar(MovimentoDto movimentoDto) {
		Processo processo = processoService.findById(movimentoDto.getProcessoId());
		Advogado advogado = advogadoService.findById(movimentoDto.getAdvogadoId());
		Movimento movimento = new Movimento(movimentoDto);
		movimento.setAdvogado(advogado);
		movimento.setProcesso(processo);
		return movimentoRepository.save(movimento);
	}
}
