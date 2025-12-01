package com.advocacia.estacio.web.controllers;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.DemandaResponde;
import com.advocacia.estacio.services.DemandaRespondeService;
import com.advocacia.estacio.services.DemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/demandas/responde")
@RestController
@CrossOrigin("http://localhost:3000")
public class DemandaRespondeController {
	
	@Autowired
	DemandaRespondeService demandaRespondeService;

	@PostMapping("/")
	public ResponseEntity<DemandaRespondeDto> salvar(@RequestBody DemandaRespondeDto dto) {
		DemandaResponde demandaResponde = demandaRespondeService.salvar(dto);
		return ResponseEntity.status(201).body(new DemandaRespondeDto(demandaResponde));
	}
}
