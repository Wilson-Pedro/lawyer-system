package com.advocacia.estacio.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.ProcessoService;

@Service
public class ProcessoServiceImpl implements ProcessoService {
	
	@Autowired
	ProcessoRepository processoRepository;

	@Override
	public Processo salvar(ProcessoRequestDto request) {
		Processo processo = new Processo(request);
		processo.setNumeroDoProcesso(gerarNumeroProcesso());
		processo.setPrazoFinal(LocalDateTime.now().plusDays(30L));
		processo.postulatoria();
		processo.setUltimaAtualizacao(LocalDateTime.now());
		return processoRepository.save(processo);
	}
	
	private Integer gerarNumeroProcesso() {
		String processo = String.format("%d%d", LocalDate.now().getYear(), processoRepository.count()+1);
		return Integer.parseInt(processo);
	}
}
