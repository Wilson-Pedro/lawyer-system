package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.entities.Advogado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.domain.enums.TipoDoAtor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AtorRepository extends JpaRepository<Ator, Long> {

	Page<Ator> findByNomeContainingIgnoreCaseAndTipoDoAtor(String nome, TipoDoAtor tipoDoAtor, Pageable pageable);

    Page<Ator> findAllByTipoDoAtor(TipoDoAtor tipoDoAtor, Pageable pageable);

    @Query("""
			SELECT new com.advocacia.estacio.domain.entities.Ator(
				atr.id,
				atr.nome
			)
			FROM tbl_ator atr WHERE atr.email = :email
			""")
    Optional<Ator> buscarIdPorEmail(@Param("email") String email);
}
