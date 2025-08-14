package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.estacio.domain.entities.Estagiario;

public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {

}
