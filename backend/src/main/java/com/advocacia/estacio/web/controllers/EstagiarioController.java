package com.advocacia.estacio.web.controllers;

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

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.records.EstagiarioMinDto;
import com.advocacia.estacio.services.EstagiarioService;

@RequestMapping("/estagiarios")
@RestController
@CrossOrigin("http://localhost:3000")
public class EstagiarioController {
	
	@Autowired
	EstagiarioService estagiarioService;

	@PostMapping("/")
	public ResponseEntity<EstagiarioDto> salvar(@RequestBody EstagiarioDto estagiarioDto) {
		EstagiarioDto dto = estagiarioService.salvar(estagiarioDto).toDto();
		return ResponseEntity.status(201).body(dto);
	}

	@GetMapping("")
	public ResponseEntity<PageResponseDto<EstagiarioDto>> buscarTodos(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<EstagiarioDto> pages = estagiarioService.buscarTodos(page, size).map(EstagiarioDto::new);
		return ResponseEntity.ok(new PageResponseDto<>(pages));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstagiarioDto> buscarPorId(@PathVariable Long id) {
		EstagiarioDto dto = estagiarioService.buscarPorId(id).toDto();
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/buscar/{nome}")
	public ResponseEntity<PageResponseDto<EstagiarioDto>> buscarEstagiario(
			@PathVariable String nome,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<Estagiario> pages = estagiarioService.buscarEstagiario(nome, page, size);
		Page<EstagiarioDto> pagesDto = pages.map(EstagiarioDto::new);
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
	
	@GetMapping("/buscarId/email/{email}")
	public ResponseEntity<EstagiarioMinDto> buscarIdPorEmail(@PathVariable String email) {
		EstagiarioMinDto dto = estagiarioService.buscarIdPorEmail(email);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarAssistido(
			@PathVariable Long id, 
			@RequestBody EstagiarioDto estagiarioDto) {
		estagiarioService.atualizar(id, estagiarioDto);
		return ResponseEntity.noContent().build();
	}
}
