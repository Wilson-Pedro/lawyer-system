package com.advocacia.estacio.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.advocacia.estacio.services.UsuarioAuthService;

@Component
public class UsuarioScheduler {

	@Autowired
	UsuarioAuthService usuarioAuthService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void desativarAtivarUsuariosPorData() {
		this.usuarioAuthService.desativarAtivarUsuariosPorData();
	}
}
