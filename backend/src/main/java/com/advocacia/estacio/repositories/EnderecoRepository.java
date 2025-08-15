package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
