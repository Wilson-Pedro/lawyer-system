package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.services.EstagiarioService;

@RequestMapping("/estagiarios")
@RestController
@CrossOrigin("*")
//@CrossOrigin("192.168.100.119:8081")
public class EstagiarioController {
	
	@Autowired
	EstagiarioService estagiarioService;

	@PostMapping("/")
	public ResponseEntity<EstagiarioDto> salvar(@RequestBody EstagiarioDto estagiarioDto) {
		Estagiario estagiario = estagiarioService.salvar(estagiarioDto);
		return ResponseEntity.status(201).body(new EstagiarioDto(estagiario));
	}
}
