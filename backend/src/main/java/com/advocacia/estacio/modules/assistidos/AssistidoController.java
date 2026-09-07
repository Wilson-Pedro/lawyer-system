package com.advocacia.estacio.modules.assistidos;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assistidos")
public class AssistidoController {
	
	@Autowired
	AssistidoService assistidoService;

	@PostMapping
	public ResponseEntity<AssistidoDTO.Response> cadastrar(
			@RequestBody @Valid AssistidoDTO.CreateRequest dados) {
		var dto = assistidoService.cadastrar(dados);
		return ResponseEntity.status(201).body(dto);
	}

	@GetMapping
	public ResponseEntity<Page<AssistidoDTO.ListResponse>> listar(
			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			AssistidoDTO.ListResponseFilter filtro
	) {
		var pages = assistidoService.listar(pageable);
		return ResponseEntity.ok(pages);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AssistidoDTO.Response> buscarPorId(@PathVariable Long id) {
		var dto = assistidoService.buscarPorId(id);
		return ResponseEntity.ok(dto);
	}

	// TODO: ENTENDER ONDE ISSO É USADO NO FRONT
//	@GetMapping("/estadosCivis")
//	public ResponseEntity<List<String>> buscarEstadosCivis() {
//		List<String> estadoCivis = assistidoService.getEstadosCivis().stream().map(EstadoCivil::getEstado).toList();
//		return ResponseEntity.ok(estadoCivis);
//	}


	//	@GetMapping("/buscar/{nome}")
//	public ResponseEntity<PageResponseDto<AssistidoDto>> buscarAssistido(
//			@PathVariable String nome,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "20") int size) {
//		Page<AssistidoDto> dtos = assistidoService.buscarAssistidoPorNome(nome, page, size)
//				.map(AssistidoDto::new);
//		return ResponseEntity.ok(new PageResponseDto<>(dtos));
//	}

	
//	@PutMapping("/{id}")
//	public ResponseEntity<Void> atualizarAssistido(
//			@PathVariable Long id,
//			@RequestBody AssistidoDto assistidoDto) {
//		assistidoService.atualizar(id, assistidoDto);
//		return ResponseEntity.noContent().build();
//	}
}
