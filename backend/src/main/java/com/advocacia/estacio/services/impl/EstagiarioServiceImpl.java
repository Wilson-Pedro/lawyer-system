package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
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
}
