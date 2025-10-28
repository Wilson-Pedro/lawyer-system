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

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.services.EstagiarioService;

@RequestMapping("/estagiarios")
@RestController
@CrossOrigin("http://localhost:3000")
public class EstagiarioController {
	
	@Autowired
	EstagiarioService estagiarioService;

	@PostMapping("/")
	public ResponseEntity<EstagiarioDto> salvar(@RequestBody EstagiarioDto estagiarioDto) {
		Estagiario estagiario = estagiarioService.salvar(estagiarioDto);
		return ResponseEntity.status(201).body(new EstagiarioDto(estagiario));
	}
	
	@GetMapping("/buscar/{nome}")
	public ResponseEntity<PageResponseDto<EstagiarioDto>> buscarEstagiario(
			@PathVariable String nome,
			@RequestParam(defaultValue = "10") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<Estagiario> pages = estagiarioService.buscarEstagiario(nome, page, size);
		Page<EstagiarioDto> pagesDto = pages.map(x -> new EstagiarioDto(x));
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
}
