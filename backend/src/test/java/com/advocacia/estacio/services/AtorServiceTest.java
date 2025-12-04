package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.dto.ResponseMinDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

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

//	@Test
//	@DisplayName("Deve buscar Todos os Assistidos")
//	void buscar_todos() {
//
//		Page<ResponseMinDto> pages = atorService.buscarTodosPorTipoDoAtor("Coordenador do curso",0, 20);
//
//		assertFalse(pages.isEmpty());
//		assertEquals(1, pages.getContent().size());
//		assertEquals("Roberto Carlos", pages.getContent().get(0).getNome("Roberto Carlos"));
//	}
}
