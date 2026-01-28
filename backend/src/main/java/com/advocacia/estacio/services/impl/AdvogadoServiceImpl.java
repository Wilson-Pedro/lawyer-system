package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.RegistroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.EnderecoService;
import com.advocacia.estacio.utils.Utils;

@Service
public class AdvogadoServiceImpl implements AdvogadoService {
	
	@Autowired
	private AdvogadoRepository advogadoRepository;
	
	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private UsuarioAuthServiceImpl usuarioAuthServiceImpl;

	@Override
	public Advogado salvar(AdvogadoDto advogadoDto) {
		RegistroDto registroDto = new RegistroDto(advogadoDto.getEmail(), advogadoDto.getSenha(), UserRole.ADVOGADO);
		UsuarioAuth auth = usuarioAuthServiceImpl.salvar(registroDto);

		Endereco endereco = enderecoService.salvar(advogadoDto);
		Advogado advogado = new Advogado(advogadoDto);

		advogado.setEndereco(endereco);
		advogado.setUsuarioAuth(auth);
		//advogado.setUsuarioStatus(UsuarioStatus.ATIVO);
		return advogadoRepository.save(advogado);
	}

	@Override
	public Advogado buscarPorId(Long id) {
		return advogadoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}
	
	@Override
	public Page<Advogado> buscarAdvogado(String nome, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
		return advogadoRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@Override
	public Page<ResponseMinDto> buscarTodos(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return advogadoRepository.buscarTodos(pageable);
	}

	@Override
	public Advogado atualizar(Long id, AdvogadoDto advogadoDto) {
		Advogado advogado = buscarPorId(id);
		UsuarioStatus usuarioStatus = UsuarioStatus.toEnum(advogadoDto.getUsuarioStatus());
		usuarioAuthServiceImpl.atualizarLogin(advogado.getEmail(), advogadoDto.getEmail(), advogadoDto.getSenha(), usuarioStatus);
		advogado.setId(id);
		advogado.setNome(advogadoDto.getNome());
		advogado.setEmail(advogadoDto.getEmail());
		advogado.setTelefone(advogadoDto.getTelefone());
		advogado.setDataDeNascimeto(Utils.localDateToString(advogadoDto.getDataDeNascimento()));
		//advogado.setUsuarioStatus(UsuarioStatus.toEnum(advogadoDto.getUsuarioStatus()));
		enderecoService.atualizar(advogado.getEndereco().getId(), new Endereco(advogadoDto));
		return advogadoRepository.save(advogado);
	}

	@Override
	public Advogado buscarIdPorEmail(String email) {
		return advogadoRepository.buscarIdPorEmail(email).orElseThrow(EntidadeNaoEncontradaException::new);
	}
}
