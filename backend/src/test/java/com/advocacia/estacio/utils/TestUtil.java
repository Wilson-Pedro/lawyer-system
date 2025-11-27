package com.advocacia.estacio.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Assistido;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.AreaDoDireito;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.StatusProcesso;
import com.advocacia.estacio.domain.enums.Tribunal;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.repositories.AdvogadoRepository;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.MovimentoRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.EstagiarioService;
import com.advocacia.estacio.services.impl.UsuarioAuthService;

@Component
public class TestUtil {
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	AtorRepository atorRepository;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	UsuarioAuthService usuarioAuthService;
	
	@Autowired
	TokenService tokenService;

	private Estagiario estagiario;
	
	private Assistido assistido;
	
	private Advogado advogado;
	
	public void deleteAll() {
		demandaRepository.deleteAll();
		movimentoRepository.deleteAll();
		processoRepository.deleteAll();
		estagiarioRepository.deleteAll();
		atorRepository.deleteAll();
		usuarioAuthRepository.deleteAll();
		assistidoRepository.deleteAll();
		advogadoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}
	
	public Processo getProcesso() {
		
		assistido = assistidoService.salvar(getAssistidoDto());
		advogado = advogadoService.salvar(getAdvogadoDto());
		estagiario = estagiarioRepository.save(getEstagiario2());
		
		return new Processo(null, assistido, "20251", "62354", "Seguro", "3210", 
				LocalDate.now().plusDays(2L), advogado.getNome(), advogado, estagiario, 
				AreaDoDireito.CIVIL, Tribunal.ESTADUAL, StatusProcesso.TRAMITANDO, "", 
				null, LocalDateTime.now());
	}
	
	public Processo salvar(ProcessoRequestDto request) {
		Assistido assistido = assistidoService.buscarPorId(request.getAssistidoId());
		Advogado advogado = advogadoService.buscarPorId(request.getAdvogadoId());
		Estagiario estagiario = estagiarioService.buscarPorId(request.getEstagiarioId());
		Processo processo = new Processo(request);
		processo.setAssistido(assistido);
		processo.setAdvogado(advogado);
		processo.setNumeroDoProcesso(gerarNumeroProcesso());
		processo.tramitando();
		processo.setEstagiario(estagiario);
		processo.setUltimaAtualizacao(LocalDateTime.now());
		validarProcesso(processo);
		return processoRepository.save(processo);
	}
	
	private String gerarNumeroProcesso() {
		return String.format("%d%d", LocalDate.now().getYear(), processoRepository.count());
	}
	
	private void validarProcesso(Processo processo) {
		if(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso())) {
			throw new NumeroDoProcessoExistenteException();
		}
	}

	public AssistidoDto getAssistidoDto() {
		return new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com","Cientista de Dados", "brasileiro", 
				"São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	}

	public AdvogadoDto getAdvogadoDto() {
		return new AdvogadoDto(null, "Carlos Silva", "carlos@gmail.com",
				"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
				"rua do passeio", 11, "53022-112");
	}
	
	public Estagiario getEstagiario() {
		return new Estagiario(
				"Pedro Lucas", "pedro@gmail.com", "20251208", 
				PeriodoEstagio.ESTAGIO_I);
	}
	
	public Estagiario getEstagiario2() {
		return new Estagiario(
				"João Lucas", "lucas@gmail.com", "20251209", 
				PeriodoEstagio.ESTAGIO_II);
	}

	public EstagiarioDto getEstagiarioDto() {
		return new EstagiarioDto(null,
				"Pedro Lucas", "pedro@gmail.com", "20251208", 
				"Estágio I", "1234");
	}

	public EstagiarioDto getEstagiarioDto2() {
		return new EstagiarioDto(null,
				"Carlos Miguel", "carlos@gmail.com", "20251309",
				"Estágio II", "1234");
	}
	
	public UsuarioAuth getUsuarioAuth() {
		String senha = new BCryptPasswordEncoder().encode("1234");
		return new UsuarioAuth("professor@gmail.com", senha, UserRole.ADMIN);
	}
	
	public AuthenticationDto getAuthenticationDto() {
		return new AuthenticationDto("professor@gmail.com", "1234");
	}
	
	public RegistroDto getRegistroDto() {
		return new RegistroDto("professor@gmail.com", "1234", UserRole.ADMIN);
	}
	
	public List<AtorDto> getAtores() {
		return List.of(
				new AtorDto(null, "Roberto Carlos", "roberto@gmail.com", "Coordenador do curso", "1234"),
				new AtorDto(null, "José Augusto", "jose@gmail.com", "Secretário", "1234"),
				new AtorDto(null, "Fabio Junior", "fabio@gmail.com", "Professor", "1234")
				);
	}
	
	public String getToken() {
		usuarioAuthRepository.save(getUsuarioAuth());		
		return usuarioAuthService.login(getAuthenticationDto()).token();
	}
}
