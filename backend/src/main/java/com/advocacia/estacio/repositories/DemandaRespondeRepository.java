package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DemandaRespondeRepository extends JpaRepository<DemandaResponde, Long> {

    @Query("""
            SELECT new com.advocacia.estacio.domain.dto.DemandaRespondeDto(
                dr.id,
                d.id,
                dr.estagiario.id,
                dr.estagiario.nome,
                dr.resposta,
                dr.respondidoPor,
                dr.registro
            )
            FROM DemandaResponde dr
            JOIN dr.demanda d
            WHERE d.id = :demandaId
            """)
    Page<DemandaRespondeDto> buscarDemandasRespostasPorDemandaId(@Param("demandaId") Long demandaId, Pageable pageable);

}
