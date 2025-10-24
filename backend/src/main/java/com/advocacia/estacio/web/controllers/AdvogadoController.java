package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
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
	
	@GetMapping("/buscar/{nome}")
	public ResponseEntity<PageResponseDto<AdvogadoDto>> buscarAdvogado(
			@PathVariable String nome,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size
			) {
		Page<Advogado> pages = advogadoService.buscarAdvogado(nome, page, size);
		Page<AdvogadoDto> pagesDto = pages.map(x -> new AdvogadoDto(x));
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
}
