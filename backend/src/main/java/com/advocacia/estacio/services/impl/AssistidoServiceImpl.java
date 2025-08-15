package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EnderecoService;

@Service
public class AssistidoServiceImpl implements AssistidoService {
	
	@Autowired
	private AssistidoRepository assistidoRepository;
	
	@Autowired
	private EnderecoService enderecoService;

	@Override
	public Assistido salvar(AssistidoDto assistidoDto) {
		Endereco endereco = enderecoService.salvar(assistidoDto);
		Assistido assistido = new Assistido(assistidoDto);
		assistido.setEndereco(endereco);
		return assistidoRepository.save(assistido);
	}
}
