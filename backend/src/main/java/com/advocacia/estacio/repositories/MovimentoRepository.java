package com.advocacia.estacio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Movimento;
import com.advocacia.estacio.domain.entities.Processo;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

	Page<Movimento> findAllByProcesso(Processo processo, Pageable pageable);
}
