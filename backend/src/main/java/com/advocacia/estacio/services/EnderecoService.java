package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Endereco;

public interface EnderecoService {

	Endereco salvar(AssistidoDto assistidoDto);
	
	Endereco salvar(AdvogadoDto advogadoDto);
}
