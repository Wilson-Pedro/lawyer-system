package com.advocacia.estacio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.projections.ProcessoProjection;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

	@Query(nativeQuery=true, value="""
			SELECT p.id, p.numero_do_processo, p.assunto, p.prazo_final, p.responsavel 
			FROM TBL_PROCESSO p 
			WHERE p.status_do_processo = 'POSTULATORIA' 
			ORDER BY p.id DESC
			""")
	List<ProcessoProjection> buscarProcessosPorStatusDoProcesso();
}
