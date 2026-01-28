package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.TipoDoAtor;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.factory.AtorFactory;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.services.AtorService;

import java.util.Arrays;
import java.util.List;

@Service
public class AtorServiceImpl implements AtorService {
	
	@Autowired
	private AtorRepository atorRepository;
	
	@Autowired
	private UsuarioAuthServiceImpl usuarioAuthServiceImpl;

	@Override
	public Ator salvar(AtorDto atorDto) {
		Ator ator = AtorFactory.criarAtor(atorDto.getTipoAtor());
		ator.setNome(atorDto.getNome());
		ator.setEmail(atorDto.getEmail());
		ator.setTipoDoAtor(TipoDoAtor.toEnum(atorDto.getTipoAtor()));

		UsuarioAuth auth = usuarioAuthServiceImpl.salvar(new RegistroDto(
				atorDto.getEmail(), 
				atorDto.getSenha(), 
				UserRole.toEnum(ator.getTipoDoAtor())));

		ator.setUsuarioAuth(auth);
		return atorRepository.save(ator);
	}

	@Override
	public Page<Ator> buscarTodosPorTipoDoAtor(String tipoDoAtor, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return atorRepository.findAllByTipoDoAtor(TipoDoAtor.toEnum(tipoDoAtor), pageable);
	}

	@Override
	public Ator buscarPorId(Long id) {
		return atorRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Ator atualizar(Long id, AtorDto atorDto) {
		Ator ator = buscarPorId(id);
		usuarioAuthServiceImpl.atualizarLogin(ator.getEmail(), atorDto.getEmail(), atorDto.getSenha(), UsuarioStatus.toEnum(atorDto.getUsuarioStatus()));
		ator.setId(id);
		ator.setNome(atorDto.getNome());
		ator.setEmail(atorDto.getEmail());
		ator.setTipoDoAtor(TipoDoAtor.toEnum(atorDto.getTipoAtor()));
		//ator.setUsuarioStatus(UsuarioStatus.toEnum(atorDto.getUsuarioStatus()));
		return atorRepository.save(ator);
	}

	@Override
	public List<TipoDoAtor> getTipoAtores() {
		return Arrays.stream(TipoDoAtor.values()).toList();
	}
}
