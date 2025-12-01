package com.advocacia.estacio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.repositories.UsuarioAuthRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioAuthRepository.findByLogin(username);
	}
}
