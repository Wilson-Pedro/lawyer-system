package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Endereco;
import com.advocacia.estacio.domain.enums.EstadoCivil;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EnderecoService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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
	
	@Override
	public Page<Assistido> buscarAssistidoPorNome(String nome, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
		return assistidoRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@Override
	public Page<ResponseMinDto> buscarTodos(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return assistidoRepository.buscarTodos(pageable);
	}

	@Override
	public Assistido buscarPorId(Long id) {
		return assistidoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Assistido atualizar(Long id, AssistidoDto assistidoDto) {
		Assistido assistido = buscarPorId(id);
		assistido.setId(id);
		assistido.setNome(assistidoDto.getNome());
		assistido.setMatricula(assistidoDto.getMatricula());
		assistido.setTelefone(assistidoDto.getTelefone());
		assistido.setEmail(assistidoDto.getEmail());
		assistido.setProfissao(assistidoDto.getProfissao());
		assistido.setNacionalidade(assistidoDto.getNacionalidade());
		assistido.setNaturalidade(assistidoDto.getNaturalidade());
		assistido.setEstadoCivil(EstadoCivil.toEnum(assistidoDto.getEstadoCivil()));
		enderecoService.atualizar(assistido.getEndereco().getId(), new Endereco(assistidoDto));
		return assistidoRepository.save(assistido);
	}

	@Override
	public List<EstadoCivil> getEstadoCivis() {
		return Arrays.stream(EstadoCivil.values()).toList();
	}
}
