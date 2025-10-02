package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.EnderecoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdvogadoServiceImpl implements AdvogadoService {
	
	@Autowired
	private AdvogadoRepository advogadoRepository;
	
	@Autowired
	private EnderecoService enderecoService;

	@Override
	public Advogado salvar(AdvogadoDto advogadoDto) {
		Endereco endereco = enderecoService.salvar(advogadoDto);
		Advogado advogado = new Advogado(advogadoDto);
		advogado.setEndereco(endereco);
		return advogadoRepository.save(advogado);
	}

	@Override
	public Advogado findById(Long id) {
		return advogadoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
