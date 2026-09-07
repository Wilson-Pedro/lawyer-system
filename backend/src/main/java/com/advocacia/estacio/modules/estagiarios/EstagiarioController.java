package com.advocacia.estacio.modules.estagiarios;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Estagiarios", description = "Operações relacionadas ao estagiário")
@RestController
@RequestMapping("/estagiarios")
@RequiredArgsConstructor
public class EstagiarioController {
	
	private final EstagiarioService estagiarioService;

	@PostMapping()
	public ResponseEntity<EstagiarioDTO.Response> cadastrar(@RequestBody @Valid EstagiarioDTO.CreateRequest request,
			UriComponentsBuilder uriBuilder)
	 {
		Estagiario estagiario = estagiarioService.cadastrar(request);
		var response = new EstagiarioDTO.Response(estagiario);
		var uri = uriBuilder.path("/estagiarios/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping("")
	public ResponseEntity<Page<EstagiarioDTO.ListResponse>> listar(
			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
			) {
		var estagiarios = estagiarioService.listar(pageable).map(EstagiarioDTO.ListResponse::new);
		return ResponseEntity.ok(estagiarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstagiarioDTO.Response> buscarPorId(@PathVariable Long id) {
		var estagiario = estagiarioService.buscarPorId(id);
		var response = new EstagiarioDTO.Response(estagiario);
		return ResponseEntity.ok(response);
	}
	
//	@GetMapping("/buscar/{nome}")
//	public ResponseEntity<PageResponseDto<EstagiarioDto>> buscarEstagiario(
//			@PathVariable String nome,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "20") int size) {
//		Page<Estagiario> pages = estagiarioService.buscarEstagiario(nome, page, size);
//		Page<EstagiarioDto> pagesDto = pages.map(EstagiarioDto::new);
//		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
//	}
	
//	@GetMapping("/buscarId/email/{email}")
//	public ResponseEntity<EntidadeMinDto> buscarIdPorEmail(@PathVariable String email) {
//		EntidadeMinDto dto = estagiarioService.buscarIdPorEmail(email);
//		return ResponseEntity.ok(dto);
//	}
	
//	@PutMapping("/{id}")
//	public ResponseEntity<Void> atualizarAssistido(
//			@PathVariable Long id,
//			@RequestBody EstagiarioDto estagiarioDto) {
//		estagiarioService.atualizar(id, estagiarioDto);
//		return ResponseEntity.noContent().build();
//	}

//	@PutMapping("/data/{id}/ativarDesativar/")
//	public ResponseEntity<Void> definirDataParaAtivarDesativar(
//			@RequestBody DesativarAtivarUsuarioPorDataDto dto,
//			@PathVariable Long id
//	) {
//		this.estagiarioService.definirDataDeDesativacao(id, dto.getDataDeDesativacao());
//		return  ResponseEntity.noContent().build();
//	}

//	@PatchMapping("/desativar/usuarios")
//	public ResponseEntity<Void> desativarUsuarios(@RequestBody RequestIds ids) {
//		this.estagiarioService.desativarEstagiarios(ids);
//		return ResponseEntity.noContent().build();
//	}

//	@PutMapping("/{usuarioStatus}/estagiarios")
//	public ResponseEntity<Void> desativarAtivarUsuariosPorData(
//			@PathVariable String usuarioStatus,
//			@RequestBody DesativarAtivarUsuarioPorDataDto data) {
//		this.estagiarioService.desativarEstagiariosPorData(data, usuarioStatus);
//		return  ResponseEntity.noContent().build();
//	}
}
