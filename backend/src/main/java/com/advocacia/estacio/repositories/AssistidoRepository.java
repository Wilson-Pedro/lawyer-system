package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Assistido;
import org.springframework.data.jpa.repository.Query;

public interface AssistidoRepository extends JpaRepository<Assistido, Long> {
	
	Page<Assistido> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.ResponseMinDto(
				ass.id,
				ass.nome,
				ass.email,
				ass.registro
			)
			FROM Assistido ass
			""")
	Page<ResponseMinDto> buscarTodos(Pageable pageable);

}
