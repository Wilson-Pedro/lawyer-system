package com.advocacia.estacio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Estagiario;

public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {
	
	Page<Estagiario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
