package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.services.AdvogadoService;

@RequestMapping("/advogados")
@RestController
@CrossOrigin("http://localhost:3000")
public class AdvogadoController {
	
	@Autowired
	AdvogadoService advogadoService;

	@PostMapping("/")
	public ResponseEntity<AdvogadoDto> salvar(@RequestBody AdvogadoDto advogadoDto) {
		Advogado advogado = advogadoService.salvar(advogadoDto);
		return ResponseEntity.status(201).body(new AdvogadoDto(advogado));
	}
}
