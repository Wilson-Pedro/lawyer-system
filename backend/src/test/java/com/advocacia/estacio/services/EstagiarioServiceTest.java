package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.dto.ResponseMinDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.domain.records.EstagiarioMinDto;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioServiceTest {
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Estagiario No Banco de Dados Pelo Service")
	void salvar_estagiario() {
		assertEquals(0, estagiarioRepository.count());
		assertEquals(0, usuarioAuthRepository.count());
		
		Estagiario estagiarioSalvo = estagiarioService.salvar(testUtil.getEstagiarioDto());
		
		assertEquals("Pedro Lucas", estagiarioSalvo.getNome());
		assertEquals("pedro@gmail.com", estagiarioSalvo.getEmail());
		assertEquals("20251208", estagiarioSalvo.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiarioSalvo.getPeriodo());
		
		UsuarioAuth userAuth = (UsuarioAuth) usuarioAuthRepository.findByLogin(estagiarioSalvo.getEmail());
		
		assertEquals(1, estagiarioRepository.count());
		assertEquals(1, usuarioAuthRepository.count());
		assertEquals(UserRole.ESTAGIARIO, userAuth.getRole());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualziar Estagiario No Banco de Dados Pelo Service")
	void atualizar_estagiario() {
		
		Long id = estagiarioRepository.findAll().get(0).getId();
		
		EstagiarioDto estagiario = new EstagiarioDto(null,
		"Pedro Silva Lucas", "pedro22@gmail.com", "92921421224","20251208",
		"Estágio II", "12345");
		
		Estagiario estagiarioAtualizado = estagiarioService.atualizar(id, estagiario);
		
		assertEquals("Pedro Silva Lucas", estagiarioAtualizado.getNome());
		assertEquals("pedro22@gmail.com", estagiarioAtualizado.getEmail());
		assertEquals("20251208", estagiarioAtualizado.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiarioAtualizado.getPeriodo());
		
		UsuarioAuth userAuth = (UsuarioAuth) 
				usuarioAuthRepository.findByLogin(estagiarioAtualizado.getEmail());
		
		assertEquals("pedro22@gmail.com", userAuth.getLogin());
		
		assertEquals(1, estagiarioRepository.count());
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Por Id No Banco de Dados Pelo Service")
	void buscar_estagiario_por_id() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		Estagiario estagiario = estagiarioService.buscarPorId(estagiarioId);

		assertEquals("Pedro Silva Lucas", estagiario.getNome());
		assertEquals("pedro22@gmail.com", estagiario.getEmail());
		assertEquals("20251208", estagiario.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiario.getPeriodo());
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Por Nome No Banco de Dados Pelo Service")
	void buscar_estagiario_por_nome() {

		Page<Estagiario> estagiarios = estagiarioService.buscarEstagiario("dro", 0, 20);

		assertEquals("Pedro Silva Lucas", estagiarios.getContent().get(0).getNome());
		assertEquals("pedro22@gmail.com", estagiarios.getContent().get(0).getEmail());
		assertEquals("92921421224", estagiarios.getContent().get(0).getTelefone());
		assertEquals("20251208", estagiarios.getContent().get(0).getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiarios.getContent().get(0).getPeriodo());
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Id Por Email No Banco de Dados Pelo Service")
	void deve_buscar_Estagiario_id_pelo_email_PeloService() {

		Estagiario estagiario = estagiarioRepository.findAll().get(0);
		EstagiarioMinDto dto = estagiarioService.buscarIdPorEmail("pedro22@gmail.com");

		assertEquals(estagiario.getId(), dto.id());
		assertEquals(estagiario.getNome(), dto.nome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Estagiarios")
	void buscar_todos() {

		Page<Estagiario> pages = estagiarioService.buscarTodos(0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("Pedro Silva Lucas", pages.getContent().get(0).getNome());
		assertEquals("pedro22@gmail.com", pages.getContent().get(0).getEmail());
		assertEquals("92921421224", pages.getContent().get(0).getTelefone());
		assertEquals("20251208", pages.getContent().get(0).getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, pages.getContent().get(0).getPeriodo());
	}
}
