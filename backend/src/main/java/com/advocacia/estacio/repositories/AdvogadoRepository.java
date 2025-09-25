package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Advogado;

public interface AdvogadoRepository extends JpaRepository<Advogado, Long> {
	
	//Page<Assistido> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
