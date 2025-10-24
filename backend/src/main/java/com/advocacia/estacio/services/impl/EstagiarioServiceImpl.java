package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.EstagiarioService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EstagiarioServiceImpl implements EstagiarioService {
	
	@Autowired
	private EstagiarioRepository estagiarioRepository;

	@Override
	public Estagiario salvar(EstagiarioDto estagiarioDto) {
		return estagiarioRepository.save(new Estagiario(estagiarioDto));
	}

	@Override
	public Estagiario buscarPorId(Long id) {
		return estagiarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	
	@Override
	public Page<Estagiario> buscarEstagiario(String nome, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
		return estagiarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}
}
