package com.advocacia.estacio.modules.processos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
//
//	@Query("""
//				SELECT new com.advocacia.estacio.domain.dto.ProcessoDto(
//					p.id,
//					p.numeroDoProcesso,
//					p.assunto,
//					p.prazoFinal,
//					p.responsavel,
//					p.statusDoProcesso,
//					adv.nome
//				)
//				FROM Processo p
//				JOIN p.advogado adv
//				WHERE p.statusDoProcesso = :statusDoProcesso
////			""")
//	Page<Processo> buscarProcessosPorStatusDoProcesso(@Param("statusDoProcesso") ProcessoStatus statusDoProcesso, Pageable pageable);
//
//	Page<Processo> findByNumeroDoProcessoContainingIgnoreCase(String numeroDoProcesso, Pageable pageable);
//
//	Optional<Processo> findByNumeroDoProcesso(String numeroDoProcesso);
	
	boolean existsByNumeroDoProcesso(String numeroDoProcesso);
}
