package com.advocacia.estacio.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.ProcessoService;

@Service
public class ProcessoServiceImpl implements ProcessoService {
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Override
	public Processo salvar(ProcessoRequestDto request) {
		Assistido assistido = assistidoService.findById(request.getAssistidoId());
		Processo processo = new Processo(request);
		processo.setAssistido(assistido);
		processo.setNumeroDoProcesso(gerarNumeroProcesso());
		processo.postulatoria();
		processo.setUltimaAtualizacao(LocalDateTime.now());
		return processoRepository.save(processo);
	}
	
	private Integer gerarNumeroProcesso() {
		String processo = String.format("%d%d", LocalDate.now().getYear(), processoRepository.count()+1);
		return Integer.parseInt(processo);
	}
	
	public List<ProcessoDto> buscarProcessosPorStatusDoProcesso() {
		return processoRepository.buscarProcessosPorStatusDoProcesso().stream()
				.map(ProcessoDto::new).toList();
	}
}
