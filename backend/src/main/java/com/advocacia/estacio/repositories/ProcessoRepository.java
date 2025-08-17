package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

}
