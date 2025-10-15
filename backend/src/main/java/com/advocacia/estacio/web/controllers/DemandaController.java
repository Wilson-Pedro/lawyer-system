package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.services.DemandaService;

@RequestMapping("/demandas")
@RestController
@CrossOrigin("http://localhost:3000")
public class DemandaController {
	
	@Autowired
	DemandaService demandaService;

	@PostMapping("/")
	public ResponseEntity<DemandaDto> salvar(@RequestBody DemandaDto demandaDto) {
		Demanda demanda = demandaService.salvar(demandaDto);
		return ResponseEntity.status(201).body(new DemandaDto(demanda));
	}
	
}
