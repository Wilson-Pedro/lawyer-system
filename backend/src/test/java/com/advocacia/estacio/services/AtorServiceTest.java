package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.domain.entities.CoordenadorDoCurso;
import com.advocacia.estacio.domain.entities.Professor;
import com.advocacia.estacio.domain.entities.Secretario;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AtorServiceTest {
	
	@Autowired
	AtorService atorService;
	
	@Autowired
	AtorRepository atorRepository;
	
	@Autowired
	UsuarioAuthRepository usuarioAuthRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_antes_dos_testes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Coordenador No Banco De Dados Pelo Service")
	void salvar_coordenador() {
		assertEquals(0, atorRepository.count());
		assertEquals(0, usuarioAuthRepository.count());
		
		Ator ator = atorService.salvar(testUtil.getAtores().get(0));
		
		assertNotNull(ator);
		assertNotNull(ator.getId());
		assertNotNull(ator.getUsuarioAuth());
		assertEquals("Roberto Carlos", ator.getNome());
		assertEquals("roberto@gmail.com", ator.getEmail());
		assertEquals("Coordenador do curso", ator.getTipoDoAtor().getTipo());
		
		UsuarioAuth userAuth = (UsuarioAuth) usuarioAuthRepository.findByLogin(ator.getEmail());
		
		assertEquals(1, atorRepository.count());
		assertEquals(1, usuarioAuthRepository.count());
		assertEquals(ator.getTipoDoAtor().getTipo(), userAuth.getRole().getRole());
	}

	@Test
	@Order(3)
	@DisplayName("Deve Salvar Secretario No Banco De Dados Pelo Service")
	void salvar_secretario() {
		
		Ator ator = atorService.salvar(testUtil.getAtores().get(1));
		
		assertNotNull(ator);
		assertNotNull(ator.getId());
		assertNotNull(ator.getUsuarioAuth());
		assertEquals("José Augusto", ator.getNome());
		assertEquals("jose@gmail.com", ator.getEmail());
		assertEquals("Secretário", ator.getTipoDoAtor().getTipo());
		
		UsuarioAuth userAuth = (UsuarioAuth) usuarioAuthRepository.findByLogin(ator.getEmail());
		
		assertEquals(2, atorRepository.count());
		assertEquals(2, usuarioAuthRepository.count());
		assertEquals(ator.getTipoDoAtor().getTipo(), userAuth.getRole().getRole());
	}
	
	@Test
	@Order(4)
	@DisplayName("Deve Salvar Professor No Banco De Dados Pelo Service")
	void salvar_professor() {
		
		Ator ator = atorService.salvar(testUtil.getAtores().get(2));
		
		assertNotNull(ator);
		assertNotNull(ator.getId());
		assertNotNull(ator.getUsuarioAuth());
		assertEquals("Fabio Junior", ator.getNome());
		assertEquals("fabio@gmail.com", ator.getEmail());
		assertEquals("Professor", ator.getTipoDoAtor().getTipo());
		
		UsuarioAuth userAuth = (UsuarioAuth) usuarioAuthRepository.findByLogin(ator.getEmail());
		
		assertEquals(3, atorRepository.count());
		assertEquals(3, usuarioAuthRepository.count());
		assertEquals(ator.getTipoDoAtor().getTipo(), userAuth.getRole().getRole());
	}
	
	@Test
	@Order(5)
	@DisplayName("Deve Atualizar Coordenador No Banco De Dados Pelo Service")
	void atualizar_coordenador() {
		
		AtorDto atorDto = new AtorDto(
				null, "Roberto Carlos Silva", "roberto22@gmail.com", 
				"Coordenador do curso", "1234");
		
		Long id = atorRepository.findAll().get(0).getId();
		
		Ator ator = atorService.atualizar(id, atorDto);
		
		assertNotNull(ator);
		assertEquals("roberto22@gmail.com", ator.getUsuarioAuth().getLogin());
		assertEquals("Roberto Carlos Silva", ator.getNome());
		assertEquals("roberto22@gmail.com", ator.getEmail());
		assertEquals("Coordenador do curso", ator.getTipoDoAtor().getTipo());
	}

	@Test
	@DisplayName("Deve buscar Todos os Coordenadores Pelo Service")
	void buscar_coordenadores() {

		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor("Coordenador do curso",0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("Roberto Carlos Silva", pages.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Secretários Pelo Service")
	void buscar_secretarios() {

		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor("Secretário",0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("José Augusto", pages.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Professores Pelo Service")
	void buscar_professores() {

		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor("Professor",0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("Fabio Junior", pages.getContent().get(0).getNome());
	}
	
	@Test
	@DisplayName("Deve Buscar Coordenador por Id No Banco De Dados Pelo Service")
	void buscar_coordenador_por_id() {
		
		Long id = atorRepository.findAll().get(0).getId();
		
		CoordenadorDoCurso coordenador = (CoordenadorDoCurso) atorService.buscarPorId(id);
		
		assertNotNull(coordenador);
		assertNotNull(coordenador.getId());
		assertNotNull(coordenador.getUsuarioAuth());
		assertEquals("Roberto Carlos Silva", coordenador.getNome());
		assertEquals("roberto22@gmail.com", coordenador.getEmail());
		assertEquals("Coordenador do curso", coordenador.getTipoDoAtor().getTipo());
	}
	
	@Test
	@DisplayName("Deve Buscar Secretário por Id No Banco De Dados Pelo Service")
	void buscar_secretário_por_id() {
		
		Long id = atorRepository.findAll().get(1).getId();
		
		Secretario secretario = (Secretario) atorService.buscarPorId(id);
		
		assertNotNull(secretario);
		assertNotNull(secretario.getId());
		assertNotNull(secretario.getUsuarioAuth());
		assertEquals("José Augusto", secretario.getNome());
		assertEquals("jose@gmail.com", secretario.getEmail());
		assertEquals("Secretário", secretario.getTipoDoAtor().getTipo());
	}
	
	@Test
	@DisplayName("Deve Buscar Professor por Id No Banco De Dados Pelo Service")
	void buscar_professor_por_id() {
		
		Long id = atorRepository.findAll().get(2).getId();
		
		Professor professor = (Professor) atorService.buscarPorId(id);
		
		assertNotNull(professor);
		assertNotNull(professor.getId());
		assertNotNull(professor.getUsuarioAuth());
		assertEquals("Fabio Junior", professor.getNome());
		assertEquals("fabio@gmail.com", professor.getEmail());
		assertEquals("Professor", professor.getTipoDoAtor().getTipo());
	}
}
