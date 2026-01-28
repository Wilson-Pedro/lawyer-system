package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.entities.Advogado;
import com.advocacia.estacio.domain.enums.DemandaStatus;
import com.advocacia.estacio.domain.enums.EstadoCivil;
import com.advocacia.estacio.domain.enums.Tempestividade;
import com.advocacia.estacio.domain.enums.UserRole;
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
	DemandaRepository demandaRepository;
	
	@Autowired
	DemandaService demandaService;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda No Banco de Dados Pelo Service")
	void salvar_demanda() {
		
		assertEquals(0, demandaRepository.count());
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());
		Advogado advogado = advogadoService.salvar(testUtil.getAdvogadoDto());

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), advogado.getId(), "Em Correção", "Aguardando Professor", "02/11/2025", 10, "Dentro do Prazo");
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
		assertEquals(DemandaStatus.EM_CORRECAO, demanda.getDemandaStatusAluno());
		assertEquals(DemandaStatus.AGUARDANDO_PROFESSOR, demanda.getDemandaStatusProfessor());

		assertEquals(1, demandaRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Service")
	void buscar_demanda_por_status() {
		
		Page<DemandaDto> demandas = demandaService.buscarTodos(0, 20);
		
		assertNotNull(demandas);
		assertEquals("Atualizar Documentos", demandas.getContent().get(0).getDemanda());
		assertEquals("Pedro Lucas", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("12/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
		assertEquals("Em Correção", demandas.getContent().get(0).getDemandaStatusAluno());
		assertEquals("Aguardando Professor", demandas.getContent().get(0).getDemandaStatusProfessor());
	}

	@Test
	@Order(4)
	@DisplayName("Deve Mudar Demanda Status Pelo Service")
	void mudar_demanda_status() {

		Long id = demandaRepository.findAll().get(0).getId();
		String status = "Devolvido";

		demandaService.mudarDemandaStatus(id, status);

		Demanda demanda = demandaRepository.findById(id).get();

		assertEquals(DemandaStatus.DEVOLVIDO, demanda.getDemandaStatusAluno());
	}
	
	@Test
	@DisplayName("Deve Buscar Demandas Pelo Estário Id No Banco de Dados Pelo Service")
	void buscar_demandas_por_estagiarioId() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		Page<DemandaDto> demandas = demandaService.buscarTodosPorEstagiarioId(estagiarioId, 0, 20);
		
		assertNotNull(demandas);
		assertEquals("Atualizar Documentos", demandas.getContent().get(0).getDemanda());
		assertEquals("Pedro Lucas", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("12/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
		assertEquals("Devolvido", demandas.getContent().get(0).getDemandaStatusAluno());
		assertEquals("Aguardando Professor", demandas.getContent().get(0).getDemandaStatusProfessor());
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Service")
	void deve_buscar_Demandas_por_status_NoBancoDeDados_PeloService() {

		Long estagiarioId2 = estagiarioService.salvar(testUtil.getEstagiarioDto2()).getId();

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto2()).getId();

		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, advogadoId, "Em Correção", "Aguardando Professor", "02/11/2025", 13, "Dentro do Prazo");
		demandaService.salvar(demandaDto2);

		Page<DemandaDto> demandas = demandaService.buscarTodosPorStatus("Em Correção", 0, 20);

		assertNotNull(demandas);
		assertEquals("Organizar Processos", demandas.getContent().get(0).getDemanda());
		assertEquals("João Miguel", demandas.getContent().get(0).getEstagiarioNome());
		assertEquals("2/11/2025", demandas.getContent().get(0).getPrazoDocumentos());
		assertEquals("15/11/2025", demandas.getContent().get(0).getPrazo());
		assertEquals("Dentro do Prazo", demandas.getContent().get(0).getTempestividade());
		assertEquals("Em Correção", demandas.getContent().get(0).getDemandaStatusAluno());
		assertEquals("Aguardando Professor", demandas.getContent().get(0).getDemandaStatusProfessor());
	}

	@Test
	@DisplayName("Deve buscar Demanda Status Do ADMIN pelo Service")
	void buscar_demanda_status_admin() {

		List<DemandaStatus> demandaStatus = demandaService.getDemandaStatus(UserRole.ADMIN);

		assertEquals(demandaStatus.size(), 8);

		assertEquals(DemandaStatus.CORRIGIDO, demandaStatus.get(0));
		assertEquals(DemandaStatus.EM_CORRECAO, demandaStatus.get(1));
		assertEquals(DemandaStatus.DEVOLVIDO, demandaStatus.get(2));
		assertEquals(DemandaStatus.DENTRO_DO_PRAZO, demandaStatus.get(3));
		assertEquals(DemandaStatus.FORA_DO_PRAZO, demandaStatus.get(4));
		assertEquals(DemandaStatus.RECEBIDO, demandaStatus.get(5));
		assertEquals(DemandaStatus.PROTOCOLADO, demandaStatus.get(6));
		assertEquals(DemandaStatus.AGUARDANDO_PROFESSOR, demandaStatus.get(7));
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
