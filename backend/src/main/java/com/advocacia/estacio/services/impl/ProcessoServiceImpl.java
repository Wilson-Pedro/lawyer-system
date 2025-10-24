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
import com.advocacia.estacio.domain.dto.ProcessoUpdate;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EstagiarioService;
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
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Override
	public Processo salvar(ProcessoRequestDto request) {
		Assistido assistido = assistidoService.findById(request.getAssistidoId());
		Advogado advogado = advogadoService.buscarPorId(request.getAdvogadoId());
		Estagiario estagiario = estagiarioService.buscarPorId(request.getEstagiarioId());
		Processo processo = new Processo(request);
		processo.setAssistido(assistido);
		processo.setAdvogado(advogado);
		processo.setNumeroDoProcesso(gerarNumeroProcesso());
		processo.tramitando();
		processo.setEstagiario(estagiario);
		processo.setUltimaAtualizacao(LocalDateTime.now());
		return processoRepository.save(processo);
	}
	
	private String gerarNumeroProcesso() {
		return String.format("%d%d", LocalDate.now().getYear(), processoRepository.count()+1);
	}
	
	public List<ProcessoDto> buscarProcessosPorStatusDoProcesso(String processoStatus) {
		return processoRepository.buscarProcessosPorStatusDoProcesso(processoStatus).stream()
				.map(ProcessoDto::new).toList();
	}
	
	public Page<Processo> buscarProcesso(String numeroDoProcesso, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("assunto").ascending());
		return processoRepository.findByNumeroDoProcessoContainingIgnoreCase(numeroDoProcesso.toString(), pageable);
	}

	@Override
	public Processo buscarPorId(Long id) {
		return processoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Processo buscarPorNumeroDoProcesso(String numeroDoProcesso) {
		return processoRepository.findByNumeroDoProcesso(numeroDoProcesso)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Processo atualizarProcesso(Long id, ProcessoUpdate processoUpdate) {
		Processo processo = buscarPorId(id);
		Estagiario estagiario = estagiarioService.buscarPorId(processoUpdate.getEstagiarioId());
		processo.setEstagiario(estagiario);
		return processoRepository.save(processo);
	}
}
