package com.advocacia.estacio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.projections.ProcessoProjection;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

	@Query(nativeQuery=true, value="""
				SELECT p.id, p.numero_do_processo, p.assunto, p.prazo_final, p.responsavel, 
				a.id AS advogado_id, a.nome AS advogado_nome 
				FROM TBL_PROCESSO p 
				JOIN TBL_ADVOGADO a ON p.advogado_id = a.id
				WHERE p.status_do_processo = 'POSTULATORIA' 
				ORDER BY p.id DESC
			""")
	List<ProcessoProjection> buscarProcessosPorStatusDoProcesso();
	
	Page<Processo> findByNumeroDoProcessoContainingIgnoreCase(String numeroDoProcesso, Pageable pageable);

	Optional<Processo> findByNumeroDoProcesso(String numeroDoProcesso);
}
