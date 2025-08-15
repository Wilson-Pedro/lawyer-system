package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Endereco;

public interface EnderecoService {

	Endereco salvar(AssistidoDto assistidoDto);
}
