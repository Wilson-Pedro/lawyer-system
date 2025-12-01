package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.advocacia.estacio.domain.records.EstagiarioMinDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.entities.UsuarioAuth;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.domain.enums.UserRole;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.UsuarioAuthRepository;
import com.advocacia.estacio.utils.TestUtil;
import org.springframework.data.domain.Page;

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
	void deveSalvar_Estagiario_NoBancoDeDadosPeloService() {
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
	void deve_buscar_Estagiario_peloId_PeloService() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		Estagiario estagiario = estagiarioService.buscarPorId(estagiarioId);

		assertEquals("Pedro Lucas", estagiario.getNome());
		assertEquals("pedro@gmail.com", estagiario.getEmail());
		assertEquals("20251208", estagiario.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiario.getPeriodo());
	}

	@Test
	void deve_buscar_Estagiario_pelo_nome_PeloService() {

		Page<Estagiario> estagiarios = estagiarioService.buscarEstagiario("dro", 0, 20);

		assertEquals("Pedro Lucas", estagiarios.getContent().get(0).getNome());
		assertEquals("pedro@gmail.com", estagiarios.getContent().get(0).getEmail());
		assertEquals("20251208", estagiarios.getContent().get(0).getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiarios.getContent().get(0).getPeriodo());
	}

	@Test
	void deve_buscar_Estagiario_id_pelo_email_PeloService() {

		Estagiario estagiario = estagiarioRepository.findAll().get(0);
		EstagiarioMinDto dto = estagiarioService.buscarIdPorEmail("pedro@gmail.com");

		assertEquals(estagiario.getId(), dto.id());
		assertEquals(estagiario.getNome(), dto.nome());
	}
}
