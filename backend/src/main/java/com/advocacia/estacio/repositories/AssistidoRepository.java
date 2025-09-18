package com.advocacia.estacio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Assistido;

public interface AssistidoRepository extends JpaRepository<Assistido, Long> {
	
	//Page<Novel> findByNovelNameContainingIgnoreCase(String novelName, Pageable pageable);
	
	Page<Assistido> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
