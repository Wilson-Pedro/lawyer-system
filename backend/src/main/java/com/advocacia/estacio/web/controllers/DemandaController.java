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

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
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
	
	@GetMapping("/estagiario/{estagiarioId}")
	public ResponseEntity<PageResponseDto<DemandaDto>> buscarTodos(
			@PathVariable Long estagiarioId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<DemandaDto> pagesDto = demandaService.buscarTodosPorEstagiarioId(estagiarioId, page, size);
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
	
	@GetMapping("/status/{demandaStatus}")
	public ResponseEntity<PageResponseDto<DemandaDto>> buscarTodosPorStatus(
			@PathVariable String demandaStatus,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		if(demandaStatus.toLowerCase().equals("todos")) {
			return ResponseEntity.ok(new PageResponseDto<>(demandaService.buscarTodos(page, size)));
		}
		Page<DemandaDto> pagesDto = demandaService.buscarTodosPorStatus(demandaStatus, page, size);
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
	
	@GetMapping
	public ResponseEntity<PageResponseDto<DemandaDto>> buscarTodos(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Page<DemandaDto> pagesDto = demandaService.buscarTodos(page, size);
		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
	}
}
