package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.domain.enums.TipoDoAtor;
import com.advocacia.estacio.factory.AtorFactory;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.services.AtorService;

@Service
public class AtorServiceImpl implements AtorService {
	
	@Autowired
	private AtorRepository atorRepository;
	
	public Ator salvar(AtorDto atorDto) {
		Ator ator = AtorFactory.criarAtor(atorDto.getTipoAtor());
		ator.setNome(atorDto.getNome());
		ator.setEmail(atorDto.getEmail());
		ator.setTipoDoAtor(TipoDoAtor.toEnum(atorDto.getTipoAtor()));
		ator.setSenha(atorDto.getSenha());
		
		return atorRepository.save(ator);
	}
}
