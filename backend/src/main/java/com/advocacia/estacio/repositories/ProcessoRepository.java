package com.advocacia.estacio.repositories;

import java.util.List;
import java.util.Optional;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.enums.StatusProcesso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.projections.ProcessoProjection;
import org.springframework.data.repository.query.Param;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

	@Query("""
				SELECT new com.advocacia.estacio.domain.dto.ProcessoDto(
					p.id,
					p.numeroDoProcesso,
					p.assunto,
					p.prazoFinal,
					p.responsavel,
					p.statusDoProcesso,
					adv.nome
				)
				FROM Processo p
				JOIN p.advogado adv
				WHERE p.statusDoProcesso = :statusDoProcesso
			""")
	Page<ProcessoDto> buscarProcessosPorStatusDoProcesso(@Param("statusDoProcesso") StatusProcesso statusDoProcesso, Pageable pageable);
	
	Page<Processo> findByNumeroDoProcessoContainingIgnoreCase(String numeroDoProcesso, Pageable pageable);

	Optional<Processo> findByNumeroDoProcesso(String numeroDoProcesso);	
	
	boolean existsByNumeroDoProcesso(String numeroDoProcesso);
}
