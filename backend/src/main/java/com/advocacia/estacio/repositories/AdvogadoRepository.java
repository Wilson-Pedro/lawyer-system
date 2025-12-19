package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Advogado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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

	@Query("""
			SELECT new com.advocacia.estacio.domain.entities.Advogado(
				adv.id,
				adv.nome
			)
			FROM Advogado adv WHERE adv.email = :email
			""")
	Optional<Advogado> buscarIdPorEmail(@Param("email") String email);
}
