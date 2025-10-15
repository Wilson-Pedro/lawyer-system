package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Demanda;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
}
