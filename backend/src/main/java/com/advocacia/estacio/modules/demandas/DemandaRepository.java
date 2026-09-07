package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.modules.demandas.Demanda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface DemandaRepository extends JpaRepository<Demanda, Long> {
	
//	@Query("""
//			SELECT new com.advocacia.estacio.domain.dto.DemandaDto(
//				d.id,
//				d.demanda,
//				d.estagiario.nome,
//				d.advogado.nome,
//				d.professor.nome,
//				d.estagiario.id,
//				d.advogado.id,
//				d.professor.id,
//				d.demandaStatusAluno,
//				d.demandaStatusProfessor,
//				d.demandaStatusAdvogado,
//				d.prazoDocumentos,
//				d.prazo,
//				d.tempestividade
//			)
//			FROM Demanda d
//			""")
//	Page<DemandaDto> getAll(Pageable pageable);

	@Query("""
		SELECT DISTINCT d FROM Demanda d
			JOIN d.movimentacoes a
				WHERE a.autor.id = :pessoaId
	""")
	Page<Demanda> buscarDemandasPorPessoa(
			@Param("pessoaId") Long pessoaId, Pageable pageable
	);
//
//	Page<Demanda> buscarTodosPorStatus(
//			@Param("demandaStatus") EtapaDemanda etapaDemanda, Pageable pageable);
}
