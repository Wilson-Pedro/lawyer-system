package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.services.AssistidoService;

@RequestMapping("/assistidos")
@RestController
public class AssistidoController {
	
	@Autowired
	AssistidoService assistidoService;

	@PostMapping("/")
	public ResponseEntity<AssistidoDto> salvar(@RequestBody AssistidoDto assistidoDto) {
		Assistido assistido = assistidoService.salvar(assistidoDto);
		return ResponseEntity.status(201).body(new AssistidoDto(assistido));
	}
}
