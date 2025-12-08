package com.advocacia.estacio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.domain.enums.TipoDoAtor;

public interface AtorRepository extends JpaRepository<Ator, Long> {

    Page<Ator> findAllByTipoDoAtor(TipoDoAtor tipoDoAtor, Pageable pageable);
}
