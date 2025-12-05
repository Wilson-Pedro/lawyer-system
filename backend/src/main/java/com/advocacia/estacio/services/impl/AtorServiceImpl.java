package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
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
import com.advocacia.estacio.factory.AtorFactory;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.services.AtorService;

@Service
public class AtorServiceImpl implements AtorService {
	
	@Autowired
	private AtorRepository atorRepository;
	
	@Autowired
	private UsuarioAuthService usuarioAuthService;

	@Override
	public Ator salvar(AtorDto atorDto) {
		Ator ator = AtorFactory.criarAtor(atorDto.getTipoAtor());
		ator.setNome(atorDto.getNome());
		ator.setEmail(atorDto.getEmail());
		ator.setTipoDoAtor(TipoDoAtor.toEnum(atorDto.getTipoAtor()));

		UsuarioAuth auth = usuarioAuthService.salvar(new RegistroDto(
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
}
