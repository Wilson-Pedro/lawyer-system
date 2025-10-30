package com.advocacia.estacio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.Processo;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.services.AdvogadoService;
import com.advocacia.estacio.services.AssistidoService;
import com.advocacia.estacio.services.MovimentoService;
import com.advocacia.estacio.services.ProcessoService;

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
	EstagiarioRepository estagiarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
//				"ana@gmail.com", "Cientista de Dados", "brasileiro", "São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
//		
//		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "carlos@gmail.com", "61946620131",
//				"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
//				"rua do passeio", 11, "53022-112");
//		
//		Estagiario estagiario = new Estagiario(
//				"Pedro Lucas", "pedro@gmail.com", "20251208", 
//				PeriodoEstagio.ESTAGIO_I, "1234");
//		
//		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
//		Long advogadoId = advogadoService.salvar(advogadoDto).getId();
//		Long estagiarioId = estagiarioRepository.save(estagiario).getId();
//		
//		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId,  estagiarioId, "Previdenciário", "Trabalho", "25/10/2025");
//		
//		Processo processo = processoService.salvar(request);
//		
//		MovimentoDto movimentoDto1 = new MovimentoDto(null, processo.getId(), advogadoId, "Documentação completa");
//		MovimentoDto movimentoDto2 = new MovimentoDto(null, processo.getId(), advogadoId, "Documentação do processo aprovada");
//		MovimentoDto movimentoDto3 = new MovimentoDto(null, processo.getId(), advogadoId, "Proesso enviado ao juri");
//		
//		movimentoService.salvar(movimentoDto1);
//		movimentoService.salvar(movimentoDto2);
//		movimentoService.salvar(movimentoDto3);
	}

}
