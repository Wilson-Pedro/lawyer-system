package com.advocacia.estacio.modules.professores;

import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    /**
     * Retorna apenas a referência (Proxy) da entidade Professor para uso em chaves estrangeiras.
     * NÃO executa um SELECT no banco de dados.
     */
    public Professor obterReferencia(Long id) {
        return professorRepository.getReferenceById(id);
    }
}
