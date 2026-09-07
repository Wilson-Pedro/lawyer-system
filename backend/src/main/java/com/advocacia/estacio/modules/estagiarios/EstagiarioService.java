package com.advocacia.estacio.modules.estagiarios;

import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.modules.usuarios.enums.UsuarioRole;

@Service
@RequiredArgsConstructor
public class EstagiarioService {

	private final EstagiarioRepository estagiarioRepository;
	private final UsuarioService usuarioService;

	@Transactional
	public Estagiario cadastrar(EstagiarioDTO.CreateRequest dados) {
		Usuario user = usuarioService.cadastrar(dados.email(), dados.senha(), UsuarioRole.ESTAGIARIO);
		Estagiario estagiario = dados.toEntity(user);
		return estagiarioRepository.save(estagiario);
	}

	public Page<Estagiario> listar(Pageable pageable) {
		return estagiarioRepository.findAll(pageable);
	}


	public Estagiario buscarPorId(Long id) {
		return estagiarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado"));
	}

//	public Page<Estagiario> listarPorNome(String nome, int page, int size) {
//		Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
//		return estagiarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
//	} // List<Pessoa> findTop10ByNomeContainingIgnoreCase(String pedacoDoNome);

	/**
	 * Retorna apenas a referência (Proxy) da entidade Estagiario para uso em chaves estrangeiras.
	 * NÃO executa um SELECT no banco de dados.
	 */
	public Estagiario obterReferecia(Long id) {
		return estagiarioRepository.getReferenceById(id);
	}


//	public EntidadeMinDto buscarIdPorEmail(String email) {
//		return estagiarioRepository.buscarEstagiarioMinPorEmail(email)
//				.orElseThrow(EntityNotFoundException::new);
//	}



	
//	@Override
//	public Estagiario atualizar(Long id, EstagiarioDto estagiarioDto) {
//		Estagiario estagiario = buscarPorId(id);
//		UsuarioStatus usuarioStatus = UsuarioStatus.toEnum(estagiarioDto.getUsuarioStatus());
//		usuarioAuthService.atualizarLogin(
//				estagiario.getEmail(),
//				estagiarioDto.getEmail(),
//				estagiarioDto.getSenha(),
//				usuarioStatus);
//		estagiario.setId(id);
//		estagiario.setNome(estagiarioDto.getNome());
//		estagiario.setEmail(estagiarioDto.getEmail());
//		estagiario.setTelefone(estagiarioDto.getTelefone());
//		estagiario.setMatricula(estagiarioDto.getMatricula());
//		estagiario.setPeriodo(PeriodoEstagio.toEnum(estagiarioDto.getPeriodo()));
//		return estagiarioRepository.save(estagiario);
//	}


//	public List<UsuarioAuth> buscarUsuariosAuthPorId(List<Long> ids) {
//		return ids.stream()
//				.map(id -> this.buscarPorId(id).getUsuarioAuth())
//				.toList();
//	}


//	public List<UsuarioAuth> buscarUsuariosAuthPorUsuarioStatus(UsuarioStatus usuarioStatus) {
//		return estagiarioRepository.findAll().stream()
//				.map(Estagiario::getUsuarioAuth)
//				.filter(u -> u.getUsuarioStatus() == usuarioStatus)
//				.toList();
//	}

//    @Override
//    public List<PeriodoEstagio> getPeriodos() {
//        return Arrays.stream(PeriodoEstagio.values()).toList();
//    }



//	public void desativarEstagiarios(RequestIds requestIds) {
//		List<UsuarioAuth> usuariosAuth = buscarUsuariosAuthPorId(requestIds.getIds());
//		this.usuarioAuthService.desativarAtivarUsuarios(usuariosAuth, UsuarioStatus.INATIVO);
//	}

//	@Override
//	public void desativarEstagiariosPorData(DesativarAtivarUsuarioPorDataDto dto, String usuarioStatus) {
//		List<UsuarioAuth> usuariosAuth = this.usuarioAuthService.buscarUsuariosAuthPorRole(UserRole.ESTAGIARIO);
//		LocalDate dataDesativacao = Utils.localDateToString(dto.getDataDeDesativacao());
//		UsuarioStatus status = UsuarioStatus.toEnum(usuarioStatus);
//		this.usuarioAuthService.desativarAtivarUsuariosPorData(dataDesativacao, usuariosAuth, status);
//	}

//	@Override
//	public void definirDataDeDesativacao(Long id, String data) {
//		this.usuarioAuthService.definirDataParaAtivarDesativar(id, data);
//	}
}
