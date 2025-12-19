package com.advocacia.estacio.repositories;

import java.util.Optional;

import com.advocacia.estacio.domain.records.EntidadeMinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.estacio.domain.entities.Estagiario;

public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {
	
	Page<Estagiario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	@Query("""
			SELECT new com.advocacia.estacio.domain.records.EntidadeMinDto(
					e.id,
					e.nome
			)
			FROM Estagiario e WHERE e.email = :email
			""")
	Optional<EntidadeMinDto> buscarEstagiarioMinPorEmail(@Param("email") String email);

//	@Query("""
//			SELECT new com.advocacia.estacio.domain.dto.ResponseMinDto(
//				est.id,
//				est.nome,
//				est.email,
//				est.registro
//			)
//			FROM Estagiario est
//			""")
//	Page<ResponseMinDto> buscarTodos(Pageable pageable);

	@Query("""
			SELECT new com.advocacia.estacio.domain.entities.Estagiario(
				est.id,
				est.nome,
				est.email,
				est.telefone,
				est.matricula,
				est.periodo
			)
			FROM Estagiario est
			""")
	Page<Estagiario> buscarTodos(Pageable pageable);
}
