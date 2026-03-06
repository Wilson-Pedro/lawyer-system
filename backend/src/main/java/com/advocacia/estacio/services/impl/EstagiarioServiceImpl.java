package com.advocacia.estacio.services.impl;

import com.advocacia.estacio.domain.dto.DesativarAtivarUsuarioPorDataDto;
import com.advocacia.estacio.domain.dto.RequestIds;
import com.advocacia.estacio.domain.enums.UsuarioStatus;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import com.advocacia.estacio.repositories.DesativarAtivarUsuarioPorDataRepository;
import com.advocacia.estacio.services.UsuarioAuthService;
import com.advocacia.estacio.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.exceptions.EntidadeNaoEncontradaException;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.EstagiarioService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.advocacia.estacio.utils.Utils.stringToLocalDate;

@Service
public class EstagiarioServiceImpl implements EstagiarioService {
	
	@Autowired
	private EstagiarioRepository estagiarioRepository;
	
	@Autowired
	private UsuarioAuthService usuarioAuthService;

	@Autowired
	private DesativarAtivarUsuarioPorDataRepository desativarAtivarUsuarioPorDataRepository;

	@Override
	public Estagiario salvar(EstagiarioDto estagiarioDto) {
		Estagiario estagiario = new Estagiario(estagiarioDto);
		
		RegistroDto registro = new RegistroDto(
				estagiarioDto.getEmail(), 
				estagiarioDto.getSenha(), 
				UserRole.ESTAGIARIO);
		
		UsuarioAuth auth = usuarioAuthService.salvar(registro);
		estagiario.setUsuarioAuth(auth);
		return estagiarioRepository.save(estagiario);
	}

	@Override
	public Estagiario buscarPorId(Long id) {
		return estagiarioRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
	}
	
	@Override
	public Page<Estagiario> buscarEstagiario(String nome, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
		return estagiarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@Override
	public EntidadeMinDto buscarIdPorEmail(String email) {
		return estagiarioRepository.buscarEstagiarioMinPorEmail(email)
				.orElseThrow(EntidadeNaoEncontradaException::new);
	}

	@Override
	public Page<Estagiario> buscarTodos(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return estagiarioRepository.buscarTodos(pageable);
	}
	
	@Override
	public Estagiario atualizar(Long id, EstagiarioDto estagiarioDto) {
		Estagiario estagiario = buscarPorId(id);
		UsuarioStatus usuarioStatus = UsuarioStatus.toEnum(estagiarioDto.getUsuarioStatus());
		usuarioAuthService.atualizarLogin(
				estagiario.getEmail(), 
				estagiarioDto.getEmail(), 
				estagiarioDto.getSenha(),
				usuarioStatus);
		estagiario.setId(id);
		estagiario.setNome(estagiarioDto.getNome());
		estagiario.setEmail(estagiarioDto.getEmail());
		estagiario.setTelefone(estagiarioDto.getTelefone());
		estagiario.setMatricula(estagiarioDto.getMatricula());
		estagiario.setPeriodo(PeriodoEstagio.toEnum(estagiarioDto.getPeriodo()));
		return estagiarioRepository.save(estagiario);
	}

	@Override
	public List<UsuarioAuth> buscarUsuariosAuthPorId(List<Long> ids) {
		return ids.stream()
				.map(id -> this.buscarPorId(id).getUsuarioAuth())
				.toList();
	}

	@Override
	public List<UsuarioAuth> buscarUsuariosAuthPorUsuarioStatus(UsuarioStatus usuarioStatus) {
		return estagiarioRepository.findAll().stream()
				.map(Estagiario::getUsuarioAuth)
				.filter(u -> u.getUsuarioStatus() == usuarioStatus)
				.toList();
	}

    @Override
    public List<PeriodoEstagio> getPeriodos() {
        return Arrays.stream(PeriodoEstagio.values()).toList();
    }


	@Override
	public void desativarEstagiarios(RequestIds requestIds) {
		List<UsuarioAuth> usuariosAuth = buscarUsuariosAuthPorId(requestIds.getIds());
		this.usuarioAuthService.desativarAtivarUsuarios(usuariosAuth, UsuarioStatus.INATIVO);
	}

	@Override
	public void desativarEstagiariosPorData(DesativarAtivarUsuarioPorDataDto dto, String usuarioStatus) {
		List<UsuarioAuth> usuariosAuth = this.usuarioAuthService.buscarUsuariosAuthPorRole(UserRole.ESTAGIARIO);
		LocalDate dataDesativacao = Utils.stringToLocalDate(dto.getDataDeDesativacao());
		UsuarioStatus status = UsuarioStatus.toEnum(usuarioStatus);
		this.usuarioAuthService.desativarAtivarUsuariosPorData(dataDesativacao, usuariosAuth, status);
	}

	@Override
	public void definirDataDeDesativacao(Long id, String data) {
		this.usuarioAuthService.definirDataParaAtivarDesativar(id, data);
	}
}
