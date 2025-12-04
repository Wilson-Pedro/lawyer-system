package com.advocacia.estacio.web.controllers;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
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

import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.services.AssistidoService;

@RequestMapping("/assistidos")
@RestController
@CrossOrigin("http://localhost:3000")
public class AssistidoController {
	
	@Autowired
	AssistidoService assistidoService;

	@PostMapping("/")
	public ResponseEntity<AssistidoDto> salvar(@RequestBody AssistidoDto assistidoDto) {
		Assistido assistido = assistidoService.salvar(assistidoDto);
		return ResponseEntity.status(201).body(new AssistidoDto(assistido));
	}

	@GetMapping("")
	public ResponseEntity<PageResponseDto<ResponseMinDto>> buscarTodos(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<ResponseMinDto> pages = assistidoService.buscarTodos(page, size);
		return ResponseEntity.ok(new PageResponseDto<>(pages));
	}
	
	@GetMapping("/buscar/{nome}")
	public ResponseEntity<PageResponseDto<AssistidoDto>> buscarAssistido(
			@PathVariable String nome,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<AssistidoDto> dtos = assistidoService.buscarAssistidoPorNome(nome, page, size)
				.map(AssistidoDto::new);
		return ResponseEntity.ok(new PageResponseDto<>(dtos));
	}
}
