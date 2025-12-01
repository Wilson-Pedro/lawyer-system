package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.entities.Ator;
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
	void deveSalvar_Ator_Coordenador_NoBancoDeDados_PeloService() {
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
	void deveSalvar_Ator_Secretario_NoBancoDeDados_PeloService() {
		
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
	void deveSalvar_Ator_Professor_NoBancoDeDados_PeloService() {
		
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
}
