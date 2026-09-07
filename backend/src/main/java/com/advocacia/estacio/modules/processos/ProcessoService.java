//package com.advocacia.estacio.services;
//
//import static com.advocacia.estacio.utils.Utils.localDateToString;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.List;
//
//import com.advocacia.estacio.utils.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import com.advocacia.estacio.domain.dto.ProcessoDto;
//import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
//import com.advocacia.estacio.domain.dto.ProcessoUpdate;
//import com.advocacia.estacio.modules.advogados.Advogado;
//import com.advocacia.estacio.modules.assistidos.Assistido;
//import com.advocacia.estacio.modules.estagiarios.Estagiario;
//import com.advocacia.estacio.modules.processos.Processo;
//import com.advocacia.estacio.modules.processos.AreaDoDireito;
//import com.advocacia.estacio.modules.processos.ProcessoStatus;
//import com.advocacia.estacio.modules.processos.Tribunal;
//import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
//import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;
//import com.advocacia.estacio.modules.processos.ProcessoRepository;
//
//@Service
//public class ProcessoService {
//
//	private final ProcessoRepository processoRepository;
//	private final AssistidoService assistidoService;
//	private final AdvogadoService advogadoService;
//	private final EstagiarioService estagiarioService;
//
//	public ProcessoService(ProcessoRepository processoRepository, AssistidoService assistidoService,
//						   AdvogadoService advogadoService, EstagiarioService estagiarioService) {
//		this.processoRepository = processoRepository;
//		this.assistidoService = assistidoService;
//		this.advogadoService = advogadoService;
//		this.estagiarioService = estagiarioService;
//	}
//
//
//	public Processo salvar(ProcessoRequestDto request) {
//		Assistido assistido = assistidoService.buscarPorId(request.getAssistidoId());
//		Advogado advogado = advogadoService.buscarPorId(request.getAdvogadoId());
//		Estagiario estagiario = estagiarioService.buscarPorId(request.getEstagiarioId());
//		Processo processo = new Processo(request);
//		processo.setAssistido(assistido);
//		processo.setAdvogado(advogado);
//		processo.tramitando();
//		processo.setEstagiario(estagiario);
//		processo.setUltimaAtualizacao(LocalDateTime.now());
//
//		validarProcesso(processo);
//
//		processo = processoRepository.save(processo);
//
//		processo.setNumeroDoProcesso(
//				gerarNumeroProcesso(processo.getId())
//		);
//
//		return processoRepository.save(processo);
//	}
//
//	private String gerarNumeroProcesso2(Long id) {
//		String dataFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.ddMM"));
//		return String.format("%s.%04d", dataFormatada, id);
//	}
//
//	private String gerarNumeroProcesso(Long id) {
//		LocalDateTime now = LocalDateTime.now();
//		return String.format("%d%s%s%s%s%s",
//				now.getYear(),
//				".",
//				addZero(now.getDayOfMonth()),
//				addZero(now.getMonthValue()),
//				".",
//				addZeroInId(id)
//		);
//	}
//
//	private String addZero(int value) {
//		String valueFormat = String.format("%d", value);
//		return valueFormat.length() == 1 ? "0" + valueFormat : valueFormat;
//	}
//
//	private String addZeroInId(Long id) {
//		String valueFormat = String.format("%d", id);
//		int length = valueFormat.length();
//		if(length == 1) {
//			valueFormat = "000" + valueFormat;
//		} else if (length == 2) {
//			valueFormat = "00" + valueFormat;
//		} else if (length == 3) {
//			valueFormat = "0" + valueFormat;
//		}
//		return valueFormat;
//	}
//
//
//	public List<Processo> findAll() {
//		return processoRepository.findAll();
//	}
//
//	public Page<Processo> findAll(int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//		return processoRepository.findAll(pageable);
//	}
//
//
//	public Page<ProcessoDto> buscarProcessosPorStatusDoProcesso(String processoStatus, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//        return processoRepository.buscarProcessosPorStatusDoProcesso(ProcessoStatus.toEnum(processoStatus), pageable);
//	}
//
//	public Page<Processo> buscarProcesso(String numeroDoProcesso, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("assunto").ascending());
//		return processoRepository.findByNumeroDoProcessoContainingIgnoreCase(numeroDoProcesso, pageable);
//	}
//
//
//	public Processo buscarPorId(Long id) {
//		return processoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
//	}
//
//
//	public Processo buscarPorNumeroDoProcesso(String numeroDoProcesso) {
//		return processoRepository.findByNumeroDoProcesso(numeroDoProcesso)
//				.orElseThrow(EntidadeNaoEncontradaException::new);
//	}
//
//
//	public Processo atualizarProcesso(Long id, ProcessoUpdate processoUpdate) {
//		Processo processo = buscarPorId(id);
//		processo.setId(id);
//		processo = dtoParaEntidade(processo, processoUpdate);
//
//		Estagiario estagiario = estagiarioService.buscarPorId(processoUpdate.getEstagiarioId());
//		Advogado advogado = advogadoService.buscarPorId(processoUpdate.getAdvogadoId());
//		processo.setAdvogado(advogado);
//		processo.setEstagiario(estagiario);
//
//		return processoRepository.save(processo);
//	}
//
//	private Processo dtoParaEntidade(Processo processo, ProcessoUpdate dto) {
//		processo.setNumeroDoProcesso(dto.getNumeroDoProcesso());
//		processo.setNumeroDoProcessoPje(dto.getNumeroDoProcessoPje());
//		processo.setAssunto(dto.getAssunto());
//		processo.setVara(dto.getVara());
//		processo.setPrazoFinal(Utils.localDateToString(dto.getPrazoFinal()));
//		processo.setResponsavel(dto.getResponsavel());
//		processo.setAreaDoDireito(AreaDoDireito.toEnum(dto.getAreaDoDireito()));
//		processo.setTribunal(Tribunal.toEnum(dto.getTribunal()));
//		processo.setStatusDoProcesso(ProcessoStatus.toEnum(dto.getStatusDoProcesso()));
//		processo.setPartesEnvolvidas(dto.getPartesEnvolvidas());
//		return processo;
//	}
//
//	public void validarProcesso(Processo processo) {
//		if(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso())) {
//			throw new NumeroDoProcessoExistenteException();
//		}
//	}
//
//
//	public List<AreaDoDireito> getAreasDoDireito() {
//		return Arrays.stream(AreaDoDireito.values()).toList();
//	}
//
//
//	public List<Tribunal> getTribunais() {
//		return Arrays.stream(Tribunal.values()).toList();
//	}
//
//
//	public List<ProcessoStatus> getProcessoStatus() {
//		return Arrays.stream(ProcessoStatus.values()).toList();
//	}
//}
