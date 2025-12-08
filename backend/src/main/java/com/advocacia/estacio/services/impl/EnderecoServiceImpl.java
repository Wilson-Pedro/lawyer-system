package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
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
	
	@Override
	public Endereco salvar(AdvogadoDto advogadoDto) {
		return enderecoRepository.save(new Endereco(advogadoDto));
	}
	
	@Override
	public Endereco buscarPorId(Long id) {
		return enderecoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Endereco atualizar(Long id, Endereco endereco) {
		Endereco enderecoAtualizado = buscarPorId(id);
		enderecoAtualizado.setId(id);
		enderecoAtualizado.setCidade(endereco.getCidade());
		enderecoAtualizado.setBairro(endereco.getBairro());
		enderecoAtualizado.setRua(endereco.getRua());
		enderecoAtualizado.setNumeroDaCasa(endereco.getNumeroDaCasa());
		enderecoAtualizado.setCep(endereco.getCep());
		return enderecoRepository.save(enderecoAtualizado);
	}
}
