package com.advocacia.estacio.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.estacio.domain.entities.Estagiario;

public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {
	
	Page<Estagiario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	@Query("""
			SELECT e.id FROM Estagiario e WHERE e.email = :email
			""")
	Optional<Long> buscarIdPorEmail(@Param("email") String email);
}
