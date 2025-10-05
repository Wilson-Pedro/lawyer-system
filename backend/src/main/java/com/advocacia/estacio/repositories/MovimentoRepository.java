package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Movimento;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

}
