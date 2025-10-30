package com.advocacia.estacio.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.domain.enums.AreaDoDireito;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.StatusProcesso;
import com.advocacia.estacio.domain.enums.Tribunal;
import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.MovimentoRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EstagiarioService;

@Component
public class TestUtil {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	EstagiarioService estagiarioService;
	
	public void deleteAll() {
		demandaRepository.deleteAll();
		movimentoRepository.deleteAll();
		processoRepository.deleteAll();
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		advogadoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}
	
	public Processo getProcesso() {
		
		AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com","Cientista de Dados", "brasileiro", "São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
		
		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "julio@gmail.com", "61946620131",
				"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
				"rua do passeio", 11, "53022-112");
		
		Estagiario estagiario2 = new Estagiario(
				"João Lucas", "lucas@gmail.com", "20251209", 
				PeriodoEstagio.ESTAGIO_II, "1234");
		
		Assistido assistido = assistidoService.salvar(assistidoDto);
		Advogado advogado = advogadoService.salvar(advogadoDto);
		Estagiario estagiario = estagiarioRepository.save(estagiario2);
		
		return new Processo(null, assistido, "20251", "62354", "Seguro", "3210", 
				LocalDate.now().plusDays(2L), advogado.getNome(), advogado, estagiario, 
				AreaDoDireito.CIVIL, Tribunal.ESTADUAL, StatusProcesso.TRAMITANDO, "", 
				null, LocalDateTime.now());
	}
	
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
		validarProcesso(processo);
		return processoRepository.save(processo);
	}
	
	private String gerarNumeroProcesso() {
		return String.format("%d%d", LocalDate.now().getYear(), processoRepository.count());
	}
	
	private void validarProcesso(Processo processo) {
		if(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso())) {
			throw new NumeroDoProcessoExistenteException();
		}
	}
}
