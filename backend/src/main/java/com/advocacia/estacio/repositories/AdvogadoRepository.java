package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Advogado;
import org.springframework.data.jpa.repository.Query;

public interface AdvogadoRepository extends JpaRepository<Advogado, Long> {
	
	Page<Advogado> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.ResponseMinDto(
				adv.id,
				adv.nome,
				adv.email,
				adv.registro
			)
			FROM Advogado adv
			""")
	Page<ResponseMinDto> buscarTodos(Pageable pageable);
}
