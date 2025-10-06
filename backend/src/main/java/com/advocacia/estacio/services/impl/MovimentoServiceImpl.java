package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		Processo processo = processoService.buscarPorId(movimentoDto.getProcessoId());
		Advogado advogado = advogadoService.buscarPorId(movimentoDto.getAdvogadoId());
		Movimento movimento = new Movimento(movimentoDto);
		movimento.setAdvogado(advogado);
		movimento.setProcesso(processo);
		return movimentoRepository.save(movimento);
	}


	@Override
	public Page<Movimento> buscarMovimentosPorProcesso(String numeroDoProcesso, int page, int size) {
		Processo processo = processoService.buscarPorNumeroDoProcesso(numeroDoProcesso);
		PageRequest pageable = PageRequest.of(page, size, Sort.by("registro").descending());
		return movimentoRepository.findAllByProcesso(processo, pageable);
	}
}
