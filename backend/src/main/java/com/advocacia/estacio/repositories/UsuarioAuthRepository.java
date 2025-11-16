package com.advocacia.estacio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.advocacia.estacio.domain.entities.UsuarioAuth;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {
	
	UserDetails findByLogin(String login);
}
