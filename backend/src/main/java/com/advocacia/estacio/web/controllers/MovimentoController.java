package com.advocacia.estacio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.entities.Movimento;
import com.advocacia.estacio.services.MovimentoService;

@RequestMapping("/movimentos")
@RestController
@CrossOrigin("http://localhost:3000")
public class MovimentoController {
	
	@Autowired
	MovimentoService movimentoService;

	@PostMapping("/")
	public ResponseEntity<MovimentoDto> salvar(@RequestBody MovimentoDto movimentoDto) {
		Movimento movimento = movimentoService.salvar(movimentoDto);
		return ResponseEntity.status(201).body(new MovimentoDto(movimento));
	}
	
//	@GetMapping("/buscar/{nome}")
//	public ResponseEntity<Page<AdvogadoDto>> buscarAdvogado(
//			@PathVariable String nome,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size
//			) {
//		Page<Advogado> pages = advogadoService.buscarAdvogado(nome, page, size);
//		Page<AdvogadoDto> pagesDto = pages.map(x -> new AdvogadoDto(x));
//		return ResponseEntity.ok(pagesDto);
//	}
}
