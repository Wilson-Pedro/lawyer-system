package com.advocacia.estacio.modules.advogados;

import com.advocacia.estacio.modules.usuarios.enums.UsuarioStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/advogados")
@RequiredArgsConstructor
public class AdvogadoController {

	private final AdvogadoService advogadoService;

	@PostMapping
	public ResponseEntity<AdvogadoDTO.Response> cadastrar(
			@RequestBody @Valid AdvogadoDTO.CreateRequest request, UriComponentsBuilder uriBuilder) {
		AdvogadoDTO.Response response = advogadoService.cadastrar(request);
		var uri = uriBuilder.path("/estagiarios/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<Page<AdvogadoDTO.ListResponse>> listar(
			@RequestParam(required = false) AdvogadoDTO.Filter filtro,
			@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		var filtroSeguro = (filtro != null) ? filtro : new AdvogadoDTO.Filter(null, null);
		var page = advogadoService.listar(filtroSeguro, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/autocomplete")
	public ResponseEntity<Page<AdvogadoDTO.AutocompleteResponse>> listarResumo(
			@RequestParam String nome,
			@PageableDefault(size = 10, sort = "pessoa.nome") Pageable pageable) {

		var page = advogadoService.listarResumo(nome, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AdvogadoDTO.Response> buscarPorId(@PathVariable Long id) {
		AdvogadoDTO.Response response = advogadoService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AdvogadoDTO.Response> atualizar(
			@PathVariable Long id, @RequestBody @Valid AdvogadoDTO.UpdateRequest request
	) {
		AdvogadoDTO.Response response = advogadoService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("{id}/desativar")
	public ResponseEntity<Void> desativar(@PathVariable Long id) {
		advogadoService.desativar(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("{id}/reativar")
	public ResponseEntity<Void> reativar(@PathVariable Long id) {
		advogadoService.reativar(id);
		return ResponseEntity.noContent().build();
	}

//	}
//
//	@GetMapping("/buscarId/email/{email}")
//	public ResponseEntity<EntidadeMinDto> buscarIdPorEmail(@PathVariable String email) {
//		Advogado advogado = advogadoService.buscarIdPorEmail(email);
//		EntidadeMinDto dto = new EntidadeMinDto(advogado.getId(), advogado.getNome());
//		return ResponseEntity.ok(dto);
//	}
//

//

}
