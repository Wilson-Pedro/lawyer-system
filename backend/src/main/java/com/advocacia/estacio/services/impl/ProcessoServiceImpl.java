package com.advocacia.estacio.services.impl;

import static com.advocacia.estacio.utils.Utils.localDateToString;

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
import com.advocacia.estacio.domain.enums.AreaDoDireito;
import com.advocacia.estacio.domain.enums.StatusProcesso;
import com.advocacia.estacio.domain.enums.Tribunal;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EstagiarioService;
import com.advocacia.estacio.services.ProcessoService;

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
		Assistido assistido = assistidoService.buscarPorId(request.getAssistidoId());
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
	
	@Override
	public List<Processo> findAll() {
		return processoRepository.findAll();
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
		return processoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Processo buscarPorNumeroDoProcesso(String numeroDoProcesso) {
		return processoRepository.findByNumeroDoProcesso(numeroDoProcesso)
				.orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Processo atualizarProcesso(Long id, ProcessoUpdate processoUpdate) {
		Processo processo = buscarPorId(id);
		processo.setId(id);
		processo = dtoParaEntidade(processo, processoUpdate);
		
		Estagiario estagiario = estagiarioService.buscarPorId(processoUpdate.getEstagiarioId());
		Advogado advogado = advogadoService.buscarPorId(processoUpdate.getAdvogadoId());
		processo.setAdvogado(advogado);
		processo.setEstagiario(estagiario);
		
		return processoRepository.save(processo);
	}
	
	private Processo dtoParaEntidade(Processo processo, ProcessoUpdate dto) {
		processo.setNumeroDoProcesso(dto.getNumeroDoProcesso());
		processo.setNumeroDoProcessoPje(dto.getNumeroDoProcessoPje());
		processo.setAssunto(dto.getAssunto());
		processo.setVara(dto.getVara());
		processo.setPrazoFinal(localDateToString(dto.getPrazoFinal()));
		processo.setResponsavel(dto.getResponsavel());
		processo.setAreaDoDireito(AreaDoDireito.toEnum(dto.getAreaDoDireito()));
		processo.setTribunal(Tribunal.toEnum(dto.getTribunal()));
		processo.setStatusDoProcesso(StatusProcesso.toEnum(dto.getStatusDoProcesso()));
		processo.setPartesEnvolvidas(dto.getPartesEnvolvidas());
		return processo;
	}
}
