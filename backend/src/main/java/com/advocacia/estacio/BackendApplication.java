package com.advocacia.estacio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.advocacia.estacio.domain.dto.AdvogadoDto;
import com.advocacia.estacio.domain.dto.AssistidoDto;
import com.advocacia.estacio.domain.dto.MovimentoDto;
import com.advocacia.estacio.domain.dto.ProcessoRequestDto;
import com.advocacia.estacio.domain.entities.Processo;
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

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AssistidoDto assistidoDto = new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
		
		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva", "julio@gmail.com", "61946620131",
				"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
				"rua do passeio", 11, "53022-112");
		
		Long assistidoId = assistidoService.salvar(assistidoDto).getId();
		Long advogadoId = advogadoService.salvar(advogadoDto).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "Seguro de Carro", "23423ee23", "Júlio", advogadoId, "25/10/2025");
		
		Processo processo = processoService.salvar(request);
		
		MovimentoDto movimentoDto1 = new MovimentoDto(null, processo.getId(), advogadoId, "Seguro de Carro1");
		MovimentoDto movimentoDto2 = new MovimentoDto(null, processo.getId(), advogadoId, "Seguro de Carro2");
		MovimentoDto movimentoDto3 = new MovimentoDto(null, processo.getId(), advogadoId, "Seguro de Carro3");
		
		movimentoService.salvar(movimentoDto1);
		movimentoService.salvar(movimentoDto2);
		movimentoService.salvar(movimentoDto3);
	}

}
