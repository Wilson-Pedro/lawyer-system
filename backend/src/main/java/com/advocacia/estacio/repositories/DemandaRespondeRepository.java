package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRespondeRepository extends JpaRepository<DemandaResponde, Long> {

}
