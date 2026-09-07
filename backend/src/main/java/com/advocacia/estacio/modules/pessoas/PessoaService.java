package com.advocacia.estacio.modules.pessoas;

import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa obterReferencia(Long pessoaId) {
        return pessoaRepository.getReferenceById(pessoaId);
    }
}
