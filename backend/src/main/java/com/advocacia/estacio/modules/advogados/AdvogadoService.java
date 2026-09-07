package com.advocacia.estacio.modules.advogados;


import com.advocacia.estacio.modules.usuarios.UsuarioService;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.enums.UsuarioRole;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoService;
import com.advocacia.estacio.modules.usuarios.enums.UsuarioStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdvogadoService {
	private final AdvogadoRepository advogadoRepository;
	private final UsuarioService usuarioService;
	private final EnderecoService enderecoService;

	@Transactional
	public AdvogadoDTO.Response cadastrar(AdvogadoDTO.CreateRequest req) {
		Endereco endereco = enderecoService.cadastrarEndereco(req.endereco());
		Usuario usuario = usuarioService.cadastrar(req.email(), req.senha(), UsuarioRole.ADVOGADO);
		Advogado advogado = req.toEntity(usuario, endereco);
		advogado = advogadoRepository.save(advogado);

		return new AdvogadoDTO.Response(advogado);
	}

	@Transactional(readOnly = true)
	public Page<AdvogadoDTO.ListResponse> listar(AdvogadoDTO.Filter filtro, Pageable pageable) {
		return advogadoRepository.pesquisarComFiltros(filtro.nome(), filtro.status(), pageable)
				.map(AdvogadoDTO.ListResponse::new);
	}

	@Transactional(readOnly = true)
	public Page<AdvogadoDTO.AutocompleteResponse> listarResumo(String nome, Pageable pageable) {
		return advogadoRepository.buscarAtivosPorNome(
				nome, UsuarioStatus.ATIVO, pageable).map(AdvogadoDTO.AutocompleteResponse::new);
	}

	@Transactional(readOnly = true)
	public AdvogadoDTO.Response buscarPorId(Long id) {
		Advogado advogado = advogadoRepository.buscarDetalhesPorId(id)
				.orElseThrow(() -> new EntityNotFoundException("Advogado não encontrado."));
		return new AdvogadoDTO.Response(advogado);
	}

	@Transactional
	public AdvogadoDTO.Response atualizar(Long id, AdvogadoDTO.UpdateRequest req) {
		Advogado advogado = buscarAdvogadoPorId(id);
		advogado.atualizarDados(req.nome(), req.telefone());
		return new AdvogadoDTO.Response(advogado);
	}

	@Transactional
	public void desativar(Long id) {
		Advogado advogado = buscarAdvogadoPorId(id);
		advogado.desativar();
	}

	@Transactional
	public void reativar(Long id) {
		Advogado advogado = buscarAdvogadoPorId(id);
		advogado.reativar();
	}

	/**
	 * Retorna apenas a referência (Proxy) da entidade Advogado para uso em chaves estrangeiras.
	 * NÃO executa um SELECT no banco de dados.
	 */
	public Advogado obterReferencia(Long id) {
		return advogadoRepository.getReferenceById(id);
	}

	// --- MÉTODOS PRIVADOS (Auxiliares internos) ---

	/**
	 * Centraliza a busca e a regra de "Não Encontrado",
	 * evitando duplicação de código.
	 */
	private Advogado buscarAdvogadoPorId(Long id) {
		return advogadoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Advogado não encontrado"));
	}


//	public Advogado buscarIdPorEmail(String email) {
//		return advogadoRepository.buscarIdPorEmail(email).orElseThrow(EntidadeNaoEncontradaException::new);
//	}


//	public List<Usuario> buscarUsuariosAuthPorId(List<Long> ids) {
//		return ids.stream()
//				.map(id -> this.buscarPorId(id).getUsuarioAuth())
//				.toList();
//	}



	// desativa vários advogados de uma vez
//	public void desativarAdvogados(RequestIds requestIds) {
//		List<Usuario> usuariosAuth = buscarUsuariosAuthPorId(requestIds.getIds());
//		this.usuarioService.desativarAtivarUsuarios(usuariosAuth,UsuarioStatus.INATIVO);
//	}
}
