package com.advocacia.estacio.web.controllers;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.dto.RequestIds;
import com.advocacia.estacio.domain.dto.refactorDto.EstagiarioListResponse;
import com.advocacia.estacio.domain.dto.refactorDto.EstagiarioRequest;
import com.advocacia.estacio.domain.dto.refactorDto.EstagiarioResponse;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.services.impl.EstagiarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.dto.PageResponseDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import com.advocacia.estacio.services.EstagiarioService;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Estagiarios", description = "Operações relacionadas ao estagiário")
@RequestMapping("/estagiarios")
@RestController
@CrossOrigin(origins = "${cors.allowed.origins}")
public class EstagiarioController {
	
	@Autowired
	EstagiarioService estagiarioService;

	@PostMapping("/")
	public ResponseEntity<EstagiarioResponse> salvar(
			@RequestBody @Valid EstagiarioRequest data,
			UriComponentsBuilder uriBuilder)
	 {
		EstagiarioResponse dto = estagiarioService.salvar(data);
		var uri = uriBuilder.path("/estagiarios/{id}").buildAndExpand(dto.id()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping("")
	public ResponseEntity<Page<EstagiarioListResponse>> buscarTodos(
			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
			) {
		var estagiarios = estagiarioService.buscarTodos(pageable);
		return ResponseEntity.ok(estagiarios);
	}

	@GetMapping("/periodos")
	public ResponseEntity<List<String>> buscarPeriodos() {
		List<String> periodos = estagiarioService.getPeriodos().stream().map(PeriodoEstagio::getTipo).toList();
		return ResponseEntity.ok(periodos);
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
	public ResponseEntity<EntidadeMinDto> buscarIdPorEmail(@PathVariable String email) {
		EntidadeMinDto dto = estagiarioService.buscarIdPorEmail(email);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarAssistido(
			@PathVariable Long id, 
			@RequestBody EstagiarioDto estagiarioDto) {
		estagiarioService.atualizar(id, estagiarioDto);
		return ResponseEntity.noContent().build();
	}

//	@PutMapping("/data/{id}/ativarDesativar/")
//	public ResponseEntity<Void> definirDataParaAtivarDesativar(
//			@RequestBody DesativarAtivarUsuarioPorDataDto dto,
//			@PathVariable Long id
//	) {
//		this.estagiarioService.definirDataDeDesativacao(id, dto.getDataDeDesativacao());
//		return  ResponseEntity.noContent().build();
//	}

	@PatchMapping("/desativar/usuarios")
	public ResponseEntity<Void> desativarUsuarios(@RequestBody RequestIds ids) {
		this.estagiarioService.desativarEstagiarios(ids);
		return ResponseEntity.noContent().build();
	}

//	@PutMapping("/{usuarioStatus}/estagiarios")
//	public ResponseEntity<Void> desativarAtivarUsuariosPorData(
//			@PathVariable String usuarioStatus,
//			@RequestBody DesativarAtivarUsuarioPorDataDto data) {
//		this.estagiarioService.desativarEstagiariosPorData(data, usuarioStatus);
//		return  ResponseEntity.noContent().build();
//	}
}
