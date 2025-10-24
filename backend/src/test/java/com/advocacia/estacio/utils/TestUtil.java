package com.advocacia.estacio.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.MovimentoRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;

@Component
public class TestUtil {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	public void deleteAll() {
		demandaRepository.deleteAll();
		movimentoRepository.deleteAll();
		processoRepository.deleteAll();
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		advogadoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}
}
