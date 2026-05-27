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
				d.estagiario.nome,
				d.advogado.nome,
				d.professor.nome,
				d.estagiario.id,
				d.advogado.id,
				d.professor.id,
				d.demandaStatusAluno,
				d.demandaStatusProfessor,
				d.prazoDocumentos,
				d.prazo,
				d.tempestividade
			)
			FROM Demanda d
			""")
	Page<DemandaDto> buscarTodos(Pageable pageable);
	
	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
				d.id,
				d.demanda,
				e.nome,
				e.id,
				d.advogado.id,
				d.demandaStatusAluno,
				d.demandaStatusProfessor,
				d.prazoDocumentos,
				d.prazo,
				d.tempestividade
			)
			FROM Demanda d
			JOIN d.estagiario e
			WHERE e.id = :userId
			""")
	Page<DemandaDto> buscarTodosPorUserId(
			@Param("userId") Long userId, Pageable pageable);

//	@Query("""
//			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
//				d.id,
//				d.demanda,
//				e.nome,
//				e.id,
//				d.demandaStatus,
//				d.prazoDocumentos,
//				d.prazo,
//				d.tempestividade
//			)
//			FROM Demanda d
//			JOIN d.estagiario e
//			WHERE e.id = :estagiarioId
//			""")
//	Page<DemandaDto> buscarTodosPorAdvogadoId(
//			@Param("advogadoId") Long advogadoId, Pageable pageable);
	
	@Query("""
			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
				d.id,
				d.demanda,
				d.estagiario.nome,
				d.advogado.nome,
				d.professor.nome,
				d.estagiario.id,
				d.advogado.id,
				d.professor.id,
				d.demandaStatusAluno,
				d.demandaStatusProfessor,
				d.prazoDocumentos,
				d.prazo,
				d.tempestividade
			)
			FROM Demanda d
			WHERE (d.demandaStatusAluno = :demandaStatus OR d.demandaStatusProfessor = :demandaStatus)
			""")
	Page<DemandaDto> buscarTodosPorStatus(
			@Param("demandaStatus") DemandaStatus demandaStatus, Pageable pageable);
}
