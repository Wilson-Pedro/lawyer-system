package com.advocacia.estacio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Advogado;

public interface AdvogadoRepository extends JpaRepository<Advogado, Long> {
	
	Page<Advogado> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
