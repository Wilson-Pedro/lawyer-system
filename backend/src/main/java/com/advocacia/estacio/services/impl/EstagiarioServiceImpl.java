package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.EstagiarioService;

@Service
public class EstagiarioServiceImpl implements EstagiarioService {
	
	@Autowired
	private EstagiarioRepository estagiarioRepository;
	
	@Autowired
	private UsuarioAuthService usuarioAuthService;

	@Override
	public Estagiario salvar(EstagiarioDto estagiarioDto) {
		Estagiario estagiario = new Estagiario(estagiarioDto);
		
		RegistroDto registro = new RegistroDto(
				estagiarioDto.getEmail(), 
				estagiarioDto.getSenha(), 
				UserRole.ESTAGIARIO);
		
		UsuarioAuth auth = usuarioAuthService.salvar(registro);
		estagiario.setUsuarioAuth(auth);
		estagiario.setUsuarioStatus(UsuarioStatus.ATIVO);
		return estagiarioRepository.save(estagiario);
	}

	@Override
	public Estagiario buscarPorId(Long id) {
		return estagiarioRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}
	
	@Override
	public Page<Estagiario> buscarEstagiario(String nome, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
		return estagiarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@Override
	public EntidadeMinDto buscarIdPorEmail(String email) {
		return estagiarioRepository.buscarEstagiarioMinPorEmail(email)
				.orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Page<Estagiario> buscarTodos(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return estagiarioRepository.buscarTodos(pageable);
	}
	
	@Override
	public Estagiario atualizar(Long id, EstagiarioDto estagiarioDto) {
		Estagiario estagiario = buscarPorId(id);
		usuarioAuthService.atualizarLogin(
				estagiario.getEmail(), 
				estagiarioDto.getEmail(), 
				estagiarioDto.getSenha());
		estagiario.setId(id);
		estagiario.setNome(estagiarioDto.getNome());
		estagiario.setEmail(estagiarioDto.getEmail());
		estagiario.setTelefone(estagiarioDto.getTelefone());
		estagiario.setMatricula(estagiarioDto.getMatricula());
		estagiario.setPeriodo(PeriodoEstagio.toEnum(estagiarioDto.getPeriodo()));
		estagiario.setUsuarioStatus(UsuarioStatus.toEnum(estagiarioDto.getUsuarioStatus()));
		return estagiarioRepository.save(estagiario);
	}
}
