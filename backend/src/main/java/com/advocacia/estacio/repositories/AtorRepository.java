package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.enums.TipoDoAtor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Ator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AtorRepository extends JpaRepository<Ator, Long> {

//    @Query("""
//			SELECT new com.advocacia.estacio.domain.dto.ResponseMinDto(
//				at.id,
//				at.nome,
//				at.email,
//				at.registro
//			)
//			FROM Ator at
//			WHERE at.tipoDoAtor = :tipoDoAtor
//			""")
//    Page<ResponseMinDto> buscarTodosCoordenadores(@Param("tipoDoAtor") TipoDoAtor tipoDoAtor, Pageable pageable);
}
