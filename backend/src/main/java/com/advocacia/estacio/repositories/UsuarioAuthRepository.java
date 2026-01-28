package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.advocacia.estacio.domain.entities.UsuarioAuth;

import java.util.List;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {
	
	UserDetails findByLogin(String login);
}
