package com.advocacia.estacio.web.controllers;

import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/{tipoAtor}")
	public ResponseEntity<PageResponseDto<Ator>> buscarTodosPorTipoAtor(
			@PathVariable String tipoAtor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor(tipoAtor, page, size);
		Page<ResponseMinDto> pagesDto = pages.map(ResponseMinDto::new);
		return ResponseEntity.ok(new PageResponseDto<>(pages));
	}
}
