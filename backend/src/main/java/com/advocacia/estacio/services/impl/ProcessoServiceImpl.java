package com.advocacia.estacio.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.ProcessoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProcessoServiceImpl implements ProcessoService {
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Override
	public Processo salvar(ProcessoRequestDto request) {
		Assistido assistido = assistidoService.findById(request.getAssistidoId());
		Advogado advogado = advogadoService.findById(request.getAdvogadoId());
		Processo processo = new Processo(request);
		processo.setAssistido(assistido);
		processo.setAdvogado(advogado);
		processo.setNumeroDoProcesso(gerarNumeroProcesso());
		processo.postulatoria();
		processo.setUltimaAtualizacao(LocalDateTime.now());
		return processoRepository.save(processo);
	}
	
	private String gerarNumeroProcesso() {
		return String.format("%d%d", LocalDate.now().getYear(), processoRepository.count()+1);
	}
	
	public List<ProcessoDto> buscarProcessosPorStatusDoProcesso() {
		return processoRepository.buscarProcessosPorStatusDoProcesso().stream()
				.map(ProcessoDto::new).toList();
	}
	
	public Page<Processo> buscarProcesso(String numeroDoProcesso, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("assunto").ascending());
		return processoRepository.findByNumeroDoProcessoContainingIgnoreCase(numeroDoProcesso.toString(), pageable);
	}

	@Override
	public Processo findById(Long id) {
		return processoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
