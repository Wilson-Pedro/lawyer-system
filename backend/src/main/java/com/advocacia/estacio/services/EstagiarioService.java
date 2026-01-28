package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;

import java.util.List;

public interface EstagiarioService {

	Estagiario salvar(EstagiarioDto estagiarioDto);
	
	Estagiario buscarPorId(Long id);

	EntidadeMinDto buscarIdPorEmail(String email);

	Page<Estagiario> buscarTodos(int page, int size);
	
	Page<Estagiario> buscarEstagiario(String nome, int page, int size);
	
	Estagiario atualizar(Long id, EstagiarioDto estagiarioDto);

	List<PeriodoEstagio> getPeriodos();
}
