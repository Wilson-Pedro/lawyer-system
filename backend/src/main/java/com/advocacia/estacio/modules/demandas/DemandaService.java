package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.modules.advogados.Advogado;

import com.advocacia.estacio.modules.demandas.movimentacoes.DemandaMovimentacao;

import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaService;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.professores.ProfessorService;
import com.advocacia.estacio.infra.security.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DemandaService {

	private final DemandaRepository demandaRepository;
	private final EstagiarioService estagiarioService;
	private final ProfessorService professorService;
	private final AdvogadoService advogadoService;
	private final PessoaService pessoaService;
	private final SecurityUtils securityUtils;


	/**
	 * Ao criar uma demanda se add partes envolvidas na demanda
	 * Adicionar os dias para calcular o Prazo Final (Prazo dos Docs + dias adicionais)
	 *
	 */
	@Transactional
	public DemandaDTO.Response cadastrar(DemandaDTO.Request dados, Long autorId) {
		Estagiario estagiario = estagiarioService.obterReferecia(dados.estagiarioId());
		Professor professor = professorService.obterReferencia(dados.professorId());
		Advogado advogado = advogadoService.obterReferencia(dados.advogadoId());

		LocalDate prazoFinal = dados.prazoDocumentos().plusDays(dados.diasAdicionais());

		Demanda demanda = Demanda.builder()
				.estagiario(estagiario)
				.professor(professor)
				.advogado(advogado)
				.descricaoDemanda(dados.descricaoDemanda())
				.prazo(prazoFinal)
				.prazoDocumentos(dados.prazoDocumentos())
				.build();

		// TODO: Com a adoção do sistema, talvez já exista demandas em andamento
		// dessa forma seria interessante verificar como vai funcionar a adição do
		// DemandaStatus ao cadastrar essas demandas.

		// TODO: Descobrir quem cadastra as demandas (coordenador, secretário ou admin).
		Long idPessoaAutor = securityUtils.getIdPessoaLogada();
		Pessoa autorPessoa = pessoaService.obterReferencia(idPessoaAutor);

		DemandaMovimentacao movimentaoInicial = DemandaMovimentacao.builder()
				.autor(autorPessoa)
				.observacoes("Demanda iniciada e enviada para análise do estagiário.")
				.etapa(EtapaDemanda.AGUARDANDO_ALUNO)
				.build();

		demanda.adicionarMovimentacao(movimentaoInicial);
		var demandaSalva = demandaRepository.save(demanda);
		return new DemandaDTO.Response(demandaSalva);
	}

	public DemandaDTO.Response buscarPorId(Long id) {
		var demanda = demandaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Demanda não encontrada"));
		return new DemandaDTO.Response(demanda);
	}

	public Page<DemandaDTO.ListResponse> listar(Pageable pageable) {
		return demandaRepository.findAll(pageable).map(DemandaDTO.ListResponse::new);
	}

	public Page<DemandaDTO.ListResponse> buscarTodosPorPessoa(Long pessoaId, Pageable pageable) {
		Page<Demanda> demandas = demandaRepository.buscarDemandasPorPessoa(pessoaId, pageable);
		return demandas.map(DemandaDTO.ListResponse::new);
	}
	
//	@Override
//	public Page<DemandaDto> buscarTodosPorUserId(Long userId, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//		return demandaRepository.buscarTodosPorUserId(userId, pageable);
//	}
//
//	@Override
//	public Page<DemandaDto> buscarTodosPorProfessorId(Long professorId, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//		return demandaRepository.buscarTodosPorProfessorId(professorId, pageable);
//	}
//
//	@Override
//	public Page<DemandaDto> buscarTodosPorAdvogadoId(Long advogadoId, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//		return demandaRepository.buscarTodosPorAdvogadoId(advogadoId, pageable);
//	}
//
//	@Override
//	public Page<DemandaDto> buscarTodosPorStatus(String demandaStatus, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//		return demandaRepository.buscarTodosPorStatus(DemandaStatus.toEnum(demandaStatus), pageable);
//	}

	/**
	 *	Estranho: Necessidade de Validacao de Role e definir exatamente que DemandaAvaliacao vai mudar.
	 * */
//	public void mudarDemandaStatus(Long id, DemandaStatusDto dto) {
//		Demanda demanda = buscarPorId(id);
//		demanda.setDemandaStatusAluno(EtapaDemanda.toEnum(dto.getDemandaStatusAluno()));
//		demanda.setDemandaStatusProfessor(EtapaDemanda.toEnum(dto.getDemandaStatusProfessor()));
//		demanda.setDemandaStatusAdvogado(EtapaDemanda.toEnum(dto.getDemandaStatusAdvogado()));
//		demandaRepository.save(demanda);
//	}

//	public List<EtapaDemanda> getDemandaStatus(UsuarioRole role) {
//		return switch (role) {
//			case ADMIN -> List.of(EtapaDemanda.values());
//
//			case PROFESSOR -> List.of(EtapaDemanda.EM_CORRECAO, EtapaDemanda.CORRIGIDO, EtapaDemanda.DEVOLVIDO,
//					EtapaDemanda.DENTRO_DO_PRAZO, EtapaDemanda.FORA_DO_PRAZO);
//
//			case ADVOGADO -> List.of(EtapaDemanda.EM_CORRECAO, EtapaDemanda.CORRIGIDO, EtapaDemanda.DEVOLVIDO);
//			default -> throw new EnumException("Essa Role não tem demandas");
//		};
//	}
}
