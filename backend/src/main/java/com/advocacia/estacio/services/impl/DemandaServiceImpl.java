package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.exceptions.EnumException;
import com.advocacia.estacio.services.AdvogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.services.DemandaService;
import com.advocacia.estacio.services.EstagiarioService;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandaServiceImpl implements DemandaService {
	
	@Autowired
	private DemandaRepository demandaRepository;
	
	@Autowired
	private EstagiarioService estagiarioService;

	@Autowired
	private AdvogadoService advogadoService;

	@Override
	public Demanda salvar(DemandaDto demandaDto) {
		Estagiario estagiario = estagiarioService.buscarPorId(demandaDto.getEstagiarioId());
		Advogado advogado = advogadoService.buscarPorId(demandaDto.getAdvogadoId());
		Demanda demanda = new Demanda(demandaDto);
		LocalDate prazo = demanda.getPrazoDocumentos().plusDays(demandaDto.getDiasPrazo());
		demanda.setPrazo(prazo);
		demanda.setEstagiario(estagiario);
		demanda.setAdvogado(advogado);
		return demandaRepository.save(demanda);
	}

	@Override
	public Demanda buscarPorId(Long id) {
		return demandaRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Page<DemandaDto> buscarTodos(int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRepository.buscarTodos(pageable);
	}
	
	@Override
	public Page<DemandaDto> buscarTodosPorEstagiarioId(Long estagiarioId, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRepository.buscarTodosPorEstagiarioId(estagiarioId, pageable);
	}


	@Override
	public Page<DemandaDto> buscarTodosPorStatus(String demandaStatus, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return demandaRepository.buscarTodosPorStatus(DemandaStatus.toEnum(demandaStatus), pageable);
	}

	@Override
	public void mudarDemandaStatus(Long id, String status) {
		Demanda demanda = buscarPorId(id);
		demanda.setDemandaStatusAluno(DemandaStatus.toEnum(status));
		demandaRepository.save(demanda);
	}

	@Override
	public List<DemandaStatus> getDemandaStatus(UserRole role) {
		return switch (role) {
			case ADMIN -> List.of(DemandaStatus.values());

			case PROFESSOR -> List.of(DemandaStatus.EM_CORRECAO, DemandaStatus.CORRIGIDO, DemandaStatus.DEVOLVIDO,
					DemandaStatus.DENTRO_DO_PRAZO, DemandaStatus.FORA_DO_PRAZO);

			case ADVOGADO -> List.of(DemandaStatus.EM_CORRECAO, DemandaStatus.CORRIGIDO, DemandaStatus.DEVOLVIDO);
			default -> throw new EnumException("Essa Role não tem demandas");
		};
	}
}
