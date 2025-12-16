package com.advocacia.estacio;

import com.advocacia.estacio.domain.dto.*;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.impl.UsuarioAuthService;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	MovimentoService movimentoService;
	
	@Autowired
	AtorService atorService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	DemandaService demandaService;

	@Autowired
	DemandaRespondeService demandaRespondeService;
	
	@Autowired
	UsuarioAuthService usuarioAuthService;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354",
				"ana@gmail.com", "Cientista de Dados", "brasileiro", "São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");

		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "carlos@gmail.com",
				"88566519808", "25/09/1996", "São Luís", "Vila Lobão",
				"rua do passeio", 11, "53022-112");

		EstagiarioDto estagiario = new EstagiarioDto(null,
				"Pedro Lucas", "pedro@gmail.com", "92921421224", "20251208",
				"Estágio I", "1234");

		EstagiarioDto estagiario2 = new EstagiarioDto(null,
				"Carlos Miguel", "carlos@gmail.com", "92921421224", "20251309",
				"Estágio II", "1234");

		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
		Long advogadoId = advogadoService.salvar(advogadoDto).getId();
		Long estagiarioId = estagiarioService.salvar(estagiario).getId();
		Long estagiarioId2 = estagiarioService.salvar(estagiario2).getId();

		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId,  estagiarioId, "Previdenciário", "Trabalho", "25/10/2025");

		Processo processo = processoService.salvar(request);

		MovimentoDto movimentoDto1 = new MovimentoDto(null, processo.getId(), advogadoId, "Documentação completa");
		MovimentoDto movimentoDto2 = new MovimentoDto(null, processo.getId(), advogadoId, "Documentação do processo aprovada");
		MovimentoDto movimentoDto3 = new MovimentoDto(null, processo.getId(), advogadoId, "Proesso enviado ao juri");

		movimentoService.salvar(movimentoDto1);
		movimentoService.salvar(movimentoDto2);
		movimentoService.salvar(movimentoDto3);

		AtorDto ator1 = new AtorDto(null, "Roberto Carlos", "roberto@gmail.com", "Coordenador do curso", "1234");
		AtorDto ator2 = new AtorDto(null, "José Augusto", "jose@gmail.com", "Secretário", "1234");
		AtorDto ator3 = new AtorDto(null, "Fabio Junior", "fabio@gmail.com", "Professor", "1234");

		atorService.salvar(ator1);
		atorService.salvar(ator2);
		atorService.salvar(ator3);

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Processos", estagiarioId, "Corrigido", "02/12/2025", 10, "Dentro do Prazo");
		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, "Em Correção","03/12/2025", 12, "Dentro do Prazo");
		Demanda demanda = demandaService.salvar(demandaDto);
		demandaService.salvar(demandaDto2);

		DemandaRespondeDto demandaRespondeDto = new DemandaRespondeDto(null, demanda.getId(), estagiarioId, "Documentação completa", "Estagiário");
		demandaRespondeService.salvar(demandaRespondeDto);

		RegistroDto registroDto = new RegistroDto("professor@gmail.com", "1234", UserRole.ADMIN);

		usuarioAuthService.salvar(registroDto);
	}
}
