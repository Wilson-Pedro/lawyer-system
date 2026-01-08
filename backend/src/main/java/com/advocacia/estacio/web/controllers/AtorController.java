package com.advocacia.estacio.web.controllers;

import com.advocacia.estacio.domain.enums.EstadoCivil;
import com.advocacia.estacio.domain.enums.TipoDoAtor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.services.AtorService;

import java.util.List;

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

	@GetMapping("/tipoAtores")
	public ResponseEntity<List<String>> buscarTipoAtores() {
		List<String> tipoAtores = atorService.getTipoAtores().stream().map(TipoDoAtor::getTipo).toList();
		return ResponseEntity.ok(tipoAtores);
	}

	@GetMapping("/tipo/{tipoAtor}")
	public ResponseEntity<PageResponseDto<ResponseMinDto>> buscarTodosPorTipoAtor(
			@PathVariable String tipoAtor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor(tipoAtor, page, size);
		Page<ResponseMinDto> pagesDto = pages.map(ResponseMinDto::new);
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AtorDto> buscarPorId(@PathVariable Long id) {
		AtorDto atorDto = atorService.buscarPorId(id).toDto();
		return ResponseEntity.ok(atorDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarAssistido(
			@PathVariable Long id, 
			@RequestBody AtorDto atorDto) {
		atorService.atualizar(id, atorDto);
		return ResponseEntity.noContent().build();
	}
}
