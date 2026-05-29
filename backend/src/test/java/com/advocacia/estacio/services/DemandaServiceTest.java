package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.DemandaStatusDto;
import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.entities.Professor;
import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.domain.enums.Tempestividade;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.repositories.AtorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.DemandaDto;
import com.advocacia.estacio.domain.entities.Demanda;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.repositories.DemandaRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaServiceTest {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;

	@Autowired
	EstagiarioService estagiarioService;

	@Autowired
	AdvogadoService advogadoService;

	@Autowired
	AtorService atorService;

	@Autowired
	AtorRepository atorRepository;

	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	DemandaService demandaService;
	
	@Autowired
	TestUtil testUtil;

	Professor professor;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {

		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda No Banco de Dados Pelo Service")
	void salvar_demanda() {

		this.professor = (Professor) atorService.salvar(testUtil.getAtores().get(2));
		
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());
		Advogado advogado = advogadoService.salvar(testUtil.getAdvogadoDto());

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), this.professor.getId(), advogado.getId(), "Em Correção", "Aguardando Professor", "Aguardando Advogado", "02/11/2025", 10, "Dentro do Prazo");
		Demanda demanda = demandaService.salvar(demandaDto);
		
		assertNotNull(demanda);
		assertNotNull(demanda.getId());
		assertNotNull(demanda.getRegistro());
		assertEquals("Atualizar Documentos", demanda.getDemanda());
		assertEquals(demanda.getEstagiario(), estagiario);
		assertEquals(demanda.getAdvogado(), advogado);
		assertEquals("2025-11-12", demanda.getPrazo().toString());
		assertEquals("2025-11-02", demanda.getPrazoDocumentos().toString());
		assertEquals(Tempestividade.DENTRO_DO_PRAZO, demanda.getTempestividade());
		assertEquals(DemandaStatus.AGUARDANDO_ALUNO, demanda.getDemandaStatusAluno());
		assertEquals(DemandaStatus.NULL, demanda.getDemandaStatusProfessor());
		assertEquals(DemandaStatus.NULL, demanda.getDemandaStatusAdvogado());

		assertEquals(1, demandaRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Buscar Todas as Demanda No Banco de Dados Pelo Service")
	void buscar_todas_as_demanda() {
		
		Page<DemandaDto> demandas = demandaService.buscarTodos(0, 20);
		
		assertNotNull(demandas);
		assertEquals("Atualizar Documentos", demandas.getContent().get(0).getDemanda());
		assertEquals("Pedro Lucas", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("Fabio Junior", demandas.getContent().get(0).getProfessorNome());
		assertEquals("Carlos Silva", demandas.getContent().get(0).getAdvogadoNome());
		assertEquals("12/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
		assertEquals("Aguardando Aluno", demandas.getContent().get(0).getDemandaStatusAluno());
		assertEquals("Null", demandas.getContent().get(0).getDemandaStatusProfessor());
		assertEquals("Null", demandas.getContent().get(0).getDemandaStatusAdvogado());
	}

	@Test
	@Order(4)
	@DisplayName("Deve Mudar Demanda Status Do aluno Pelo Service")
	void mudar_demanda_status_do_aluno() {

		Long id = demandaRepository.findAll().get(0).getId();
		DemandaStatusDto demandaStatusDto = new DemandaStatusDto("Devolvido", "Null", "Null");

		demandaService.mudarDemandaStatus(id, demandaStatusDto);

		Demanda demanda = demandaRepository.findById(id).get();

		assertEquals(DemandaStatus.DEVOLVIDO, demanda.getDemandaStatusAluno());
	}

	@Test
	@Order(5)
	@DisplayName("Deve Mudar Demanda Status Do professor Pelo Service")
	void mudar_demanda_status_do_professor() {

		Long id = demandaRepository.findAll().get(0).getId();
		DemandaStatusDto demandaStatusDto = new DemandaStatusDto("Devolvido", "Recebido", "Null");

		demandaService.mudarDemandaStatus(id, demandaStatusDto);

		Demanda demanda = demandaRepository.findById(id).get();

		assertEquals(DemandaStatus.RECEBIDO, demanda.getDemandaStatusProfessor());
	}


	@Test
	@Order(6)
	@DisplayName("Deve Mudar Demanda Status Do advogado Pelo Service")
	void mudar_demanda_status_do_advogado() {

		Long id = demandaRepository.findAll().get(0).getId();
		DemandaStatusDto demandaStatusDto = new DemandaStatusDto("Devolvido", "Recebido", "Protocolado");

		demandaService.mudarDemandaStatus(id, demandaStatusDto);

		Demanda demanda = demandaRepository.findById(id).get();

		assertEquals(DemandaStatus.PROTOCOLADO, demanda.getDemandaStatusAdvogado());
	}
	
	@Test
	@DisplayName("Deve Buscar Demandas Pelo Estário Id No Banco de Dados Pelo Service")
	void buscar_demandas_por_estagiarioId() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		Page<DemandaDto> demandas = demandaService.buscarTodosPorUserId(estagiarioId, 0, 20);
		
		assertNotNull(demandas);
		assertEquals("Atualizar Documentos", demandas.getContent().get(0).getDemanda());
		assertEquals("Pedro Lucas", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("12/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
		assertEquals("Devolvido", demandas.getContent().get(0).getDemandaStatusAluno());
		assertEquals("Recebido", demandas.getContent().get(0).getDemandaStatusProfessor());
		assertEquals("Protocolado", demandas.getContent().get(0).getDemandaStatusAdvogado());
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Service")
	void deve_buscar_Demandas_por_status_NoBancoDeDados_PeloService() {

		Long professorId = atorRepository.findAll().get(0).getId();

		Long estagiarioId2 = estagiarioService.salvar(testUtil.getEstagiarioDto2()).getId();

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto2()).getId();

		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, professorId, advogadoId, "Em Correção", "Aguardando Professor", "Aguardando Advogado", "02/11/2025", 13, "Dentro do Prazo");
		demandaService.salvar(demandaDto2);

		Page<DemandaDto> demandas = demandaService.buscarTodosPorStatus("Aguardando Aluno", 0, 20);

		assertNotNull(demandas);
		assertEquals("Organizar Processos", demandas.getContent().get(0).getDemanda());
		assertEquals("João Miguel", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("Fabio Junior", demandas.getContent().get(0).getProfessorNome());
		assertEquals("Mauricio Silva", demandas.getContent().get(0).getAdvogadoNome());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("15/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
		assertEquals("Aguardando Aluno", demandas.getContent().get(0).getDemandaStatusAluno());
		assertEquals("Null", demandas.getContent().get(0).getDemandaStatusProfessor());
		assertEquals("Null", demandas.getContent().get(0).getDemandaStatusAdvogado());
	}

	@Test
	@DisplayName("Deve buscar Demanda Status Do ADMIN pelo Service")
	void buscar_demanda_status_admin() {

		List<DemandaStatus> demandaStatus = demandaService.getDemandaStatus(UserRole.ADMIN);

		assertEquals(demandaStatus.size(), DemandaStatus.values().length);

		assertEquals(DemandaStatus.CORRIGIDO, demandaStatus.get(0));
		assertEquals(DemandaStatus.EM_CORRECAO, demandaStatus.get(1));
		assertEquals(DemandaStatus.DEVOLVIDO, demandaStatus.get(2));
		assertEquals(DemandaStatus.DENTRO_DO_PRAZO, demandaStatus.get(3));
		assertEquals(DemandaStatus.FORA_DO_PRAZO, demandaStatus.get(4));
		assertEquals(DemandaStatus.RECEBIDO, demandaStatus.get(5));
		assertEquals(DemandaStatus.PROTOCOLADO, demandaStatus.get(6));
		assertEquals(DemandaStatus.AGUARDANDO_PROFESSOR, demandaStatus.get(7));
		assertEquals(DemandaStatus.AGUARDANDO_ADVOGADO, demandaStatus.get(8));
		assertEquals(DemandaStatus.AGUARDANDO_ALUNO, demandaStatus.get(9));
	}

	@Test
	@DisplayName("Deve buscar Demanda Status Do PROFESSOR pelo Service")
	void buscar_demanda_status_professor() {

		List<DemandaStatus> demandaStatus = demandaService.getDemandaStatus(UserRole.PROFESSOR);

		assertEquals(5, demandaStatus.size());

		assertEquals(DemandaStatus.EM_CORRECAO, demandaStatus.get(0));
		assertEquals(DemandaStatus.CORRIGIDO, demandaStatus.get(1));
		assertEquals(DemandaStatus.DEVOLVIDO, demandaStatus.get(2));
		assertEquals(DemandaStatus.DENTRO_DO_PRAZO, demandaStatus.get(3));
		assertEquals(DemandaStatus.FORA_DO_PRAZO, demandaStatus.get(4));
	}
	@Test
	@DisplayName("Deve buscar Demanda Status Do ADVOGADO pelo Service")
	void buscar_demanda_status_advogado() {

		List<DemandaStatus> demandaStatus = demandaService.getDemandaStatus(UserRole.ADVOGADO);

		assertEquals(3, demandaStatus.size());

		assertEquals(DemandaStatus.EM_CORRECAO, demandaStatus.get(0));
		assertEquals(DemandaStatus.CORRIGIDO, demandaStatus.get(1));
		assertEquals(DemandaStatus.DEVOLVIDO, demandaStatus.get(2));
	}
}
