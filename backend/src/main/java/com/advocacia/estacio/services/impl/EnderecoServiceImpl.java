package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.services.EnderecoService;

@Service
public class EnderecoServiceImpl implements EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Override
	public Endereco salvar(AssistidoDto assistidoDto) {
		return enderecoRepository.save(new Endereco(assistidoDto));
	}
}
