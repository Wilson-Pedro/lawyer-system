package com.advocacia.estacio.modules.pessoas;

import org.springframework.data.jpa.repository.JpaRepository;

interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
