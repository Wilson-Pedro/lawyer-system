package com.advocacia.estacio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.enums.DemandaStatus;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
	
	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
				d.id,
				d.demanda,
				e.nome,
				e.id,
				d.demandaStatus,
				d.prazoDocumentos,
				d.prazo,
				d.tempestividade
			)
			FROM Demanda d
			JOIN d.estagiario e
			""")
	Page<DemandaDto> buscarTodos(Pageable pageable);
	
	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
				d.id,
				d.demanda,
				e.nome,
				e.id,
				d.demandaStatus,
				d.prazoDocumentos,
				d.prazo,
				d.tempestividade
			)
			FROM Demanda d
			JOIN d.estagiario e
			WHERE e.id = :estagiarioId
			""")
	Page<DemandaDto> buscarTodosPorEstagiarioId(
			@Param("estagiarioId") Long estagiarioId, Pageable pageable);
	
	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
				d.id,
				d.demanda,
				e.nome,
				e.id,
				d.demandaStatus,
				d.prazoDocumentos,
				d.prazo,
				d.tempestividade
			)
			FROM Demanda d
			JOIN d.estagiario e
			WHERE d.demandaStatus = :demandaStatus
			""")
	Page<DemandaDto> buscarTodosPorStatus(
			@Param("demandaStatus") DemandaStatus demandaStatus, Pageable pageable);
}
