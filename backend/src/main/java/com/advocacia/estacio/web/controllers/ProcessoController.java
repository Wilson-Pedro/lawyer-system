package com.advocacia.estacio.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.ProcessoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.services.ProcessoService;

@RequestMapping("/processos")
@RestController
@CrossOrigin("http://localhost:3000")
public class ProcessoController {
	
	@Autowired
	ProcessoService processoService;

	@PostMapping("/")
	public ResponseEntity<ProcessoDto> salvar(@RequestBody ProcessoRequestDto request) {
		Processo processo = processoService.salvar(request);
		return ResponseEntity.status(201).body(new ProcessoDto(processo));
	}
	
	@GetMapping("/statusDoProcesso")
	public ResponseEntity<List<ProcessoDto>> buscarProcessosPorStatusDoProcesso() {
		List<ProcessoDto> processos = processoService.buscarProcessosPorStatusDoProcesso();
		return ResponseEntity.ok(processos);
	}
}
