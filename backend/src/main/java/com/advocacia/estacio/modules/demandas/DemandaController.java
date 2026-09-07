package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.modules.demandas.DemandaService;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.infra.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/demandas")
@RestController
public class DemandaController {
	
	private final DemandaService demandaService;

	public DemandaController(DemandaService demandaService) {
		this.demandaService = demandaService;
	}

	// TODO: add Uri
	@PostMapping
	public ResponseEntity<DemandaDTO.Response> cadastrar(
			@RequestBody @Valid DemandaDTO.Request dados,
			@AuthenticationPrincipal CustomUserDetails usuarioLogado,
			UriComponentsBuilder uriBuilder) {

		var dto = demandaService.cadastrar(dados, usuarioLogado.getId());
		var uri = uriBuilder.path("/demandas/{id}").buildAndExpand(dto.id()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping
	public ResponseEntity<Page<DemandaDTO.ListResponse>> listar(
			@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable
	) {
		var pages = demandaService.listar(pageable);
		return ResponseEntity.ok(pages);
	}

    @GetMapping("/{demandaId}")
    public ResponseEntity<DemandaDTO.Response> buscarPorId(@PathVariable Long demandaId) {
        var dto = demandaService.buscarPorId(demandaId);
        return ResponseEntity.ok(dto);
    }

	@GetMapping("/me")
	public ResponseEntity<Page<DemandaDTO.ListResponse>> buscarMinhasDemandas(
			@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal Usuario usuarioLogado
			) {
		Long meuId = usuarioLogado.getId();
		var pages = demandaService.buscarTodosPorPessoa(meuId, pageable);
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/pessoa/{pessoaId}")
	public ResponseEntity<Page<DemandaDTO.ListResponse>> buscarDemandasPorPessoa(
			@PathVariable Long pessoaId,
			@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable) {

		var pages = demandaService.buscarTodosPorPessoa(pessoaId, pageable);
		return ResponseEntity.ok(pages);
	}

//	@GetMapping("/role/{role}")
//	public ResponseEntity<List<String>> buscarDemandaStatus(@PathVariable String role) {
//		List<String> demandaStatus = demandaService
//				.getDemandaStatus(UsuarioRole.toEnum(role))
//				.stream()
//				.map(EtapaDemanda::getStatus)
//				.toList();
//		return ResponseEntity.ok(demandaStatus);
	}

//    @PatchMapping("/{id}/change")
//    public ResponseEntity<Void> mudarDemandaStatus(@PathVariable Long id, @RequestParam(defaultValue = "Em Correção") String status) {
//        demandaService.mudarDemandaStatus(id, status);
//        return ResponseEntity.noContent().build();
//    }

//	@PutMapping("/{id}/update")
//	public ResponseEntity<Void> mudarDemandaStatus(
//			@PathVariable Long id,
//			@RequestBody DemandaStatusDto demandaStatusDto) {
//		demandaService.mudarDemandaStatus(id, demandaStatusDto);
//		return ResponseEntity.noContent().build();
//	}

//	@GetMapping("/status/{demandaStatus}")
//	public ResponseEntity<PageResponseDto<DemandaDto>> buscarTodosPorStatus(
//			@PathVariable String demandaStatus,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "20") int size) {
//		if(demandaStatus.equalsIgnoreCase("todos")) {
//			return ResponseEntity.ok(new PageResponseDto<>(demandaService.buscarTodos(page, size)));
//		}
//		Page<DemandaDto> pagesDto = demandaService.buscarTodosPorStatus(demandaStatus, page, size);
//		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
//	}

