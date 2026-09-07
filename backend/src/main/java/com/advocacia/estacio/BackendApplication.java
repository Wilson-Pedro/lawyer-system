package com.advocacia.estacio;

import com.advocacia.estacio.modules.usuarios.enums.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BackendApplication implements CommandLineRunner {
	
//	@Autowired
//	AdvogadoService advogadoService;
//
//	private final AssistidoService assistidoService;
//
//	@Autowired
//	ProcessoService processoService;
//
//	@Autowired
//	MovimentoService movimentoService;
//
//	@Autowired
//	AtorService atorService;
//
//	@Autowired
//	DesativarAtivarUsuarioPorDataRepository desativarAtivarUsuarioPorDataRepository;
//
//	@Autowired
//	EstagiarioService estagiarioService;
//
//	@Autowired
//	DemandaService demandaService;
//
//	@Autowired
//	DemandaRespondeService demandaRespondeService;
//

	private final UsuarioService usuarioService;

	public BackendApplication(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var admin = usuarioService.cadastrar("admin@gmail.com", "1234", UsuarioRole.ADMIN);

//		AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354",
//				"ana@gmail.com", "Cientista de Dados", "brasileiro", "São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
//
//		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "carlos@gmail.com",
//				"88566519808", "25/09/1996", "São Luís", "Vila Lobão",
//				"rua do passeio", 11, "53022-112", "1234");
//
//		EstagiarioRequest estagiario1 = new EstagiarioRequest(
//				"Pedro Lucas", // nome
//				"pedro@gmail.com", // email
//				"92956721128",     // telefone
//				"20251208",        // matricula
//				PeriodoEstagio.ESTAGIO_III,     // periodo
//				"1234"             // senha
//		);
//
//		EstagiarioRequest estagiario2 = new EstagiarioRequest(
//				"João Miguel", "joao@gmail.com", "92921421224", "20251309",
//				PeriodoEstagio.ESTAGIO_II, "1234");
//
//		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
//		Long advogadoId = advogadoService.salvar(advogadoDto).getId();
//		Long estagiarioId = estagiarioService.salvar(estagiario1).id();
//		Long estagiarioId2 = estagiarioService.salvar(estagiario2).id();
//
//		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId,  estagiarioId, "Previdenciário", "Trabalho", "25/10/2025");
//		Processo processo = processoService.salvar(request);
//
//		MovimentoDto movimentoDto1 = new MovimentoDto(null, processo.getId(), advogadoId, "Documentação completa");
//		MovimentoDto movimentoDto2 = new MovimentoDto(null, processo.getId(), advogadoId, "Documentação do processo aprovada");
//		MovimentoDto movimentoDto3 = new MovimentoDto(null, processo.getId(), advogadoId, "Proesso enviado ao juri");
//
//		movimentoService.salvar(movimentoDto1);
//		movimentoService.salvar(movimentoDto2);
//		movimentoService.salvar(movimentoDto3);
//
//		AtorDto ator1 = new AtorDto(null, "Roberto Carlos", "roberto@gmail.com", "Coordenador do curso", "1234");
//		AtorDto ator2 = new AtorDto(null, "José Augusto", "jose@gmail.com", "Secretário", "1234");
//		AtorDto ator3 = new AtorDto(null, "Fabio Junior", "fabio@gmail.com", "Professor", "1234");
//
//		atorService.salvar(ator1);
//		atorService.salvar(ator2);
//		Long professorId = atorService.salvar(ator3).getId();
//
//		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Processos", estagiarioId, professorId, advogadoId, "Corrigido", "Corrigido", "Aguardando Advogado", "02/12/2025", 10, "Dentro do Prazo");
//		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, professorId, advogadoId, "Em Correção", "Aguardando Professor", "Aguardando Advogado", "03/12/2025", 12, "Dentro do Prazo");
//		Demanda demanda = demandaService.salvar(demandaDto);
//		demandaService.salvar(demandaDto2);
//
//		DemandaRespondeDto demandaRespondeDto = new DemandaRespondeDto(null, demanda.getId(), estagiarioId, "Documentação completa", "Estagiário");
//		demandaRespondeService.salvar(demandaRespondeDto);
//
//		DesativarAtivarUsuarioPorData desativarAtivarUsuarioPorData = new DesativarAtivarUsuarioPorData(UserRole.ESTAGIARIO, null, UsuarioStatus.INATIVO);
//		DesativarAtivarUsuarioPorData dataParaAtivarUsuarios = new DesativarAtivarUsuarioPorData(UserRole.ESTAGIARIO, null, UsuarioStatus.ATIVO);
//		this.desativarAtivarUsuarioPorDataRepository.save(desativarAtivarUsuarioPorData);
//		this.desativarAtivarUsuarioPorDataRepository.save(dataParaAtivarUsuarios);
//
//
//		RegistroDto registroDto = new RegistroDto("admin@gmail.com", "1234", UserRole.ADMIN);
//
//		usuarioAuthServiceImpl.salvar(registroDto);
	}
}
