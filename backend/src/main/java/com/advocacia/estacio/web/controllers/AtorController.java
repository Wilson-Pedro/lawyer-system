package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.services.AtorService;

@RequestMapping("/atores")
@RestController
@CrossOrigin("http://localhost:3000")
public class AtorController {
	
	@Autowired
	AtorService atorService;

	@PostMapping("/")
	public ResponseEntity<AtorDto> salvar(@RequestBody AtorDto atorDto) {
		Ator ator = atorService.salvar(atorDto);
		return ResponseEntity.status(201).body(new AtorDto(ator));
	}
}
