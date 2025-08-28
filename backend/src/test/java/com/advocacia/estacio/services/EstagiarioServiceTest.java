package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.dto.EstagiarioDto;
import com.advocacia.estacio.domain.entities.Estagiario;
import com.advocacia.estacio.domain.enums.PeriodoEstagio;
import com.advocacia.estacio.repositories.AssistidoRepository;
import com.advocacia.estacio.repositories.EnderecoRepository;
import com.advocacia.estacio.repositories.EstagiarioRepository;
import com.advocacia.estacio.repositories.ProcessoRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioServiceTest {
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	Estagiario estagiario;
	
	@BeforeEach
	void setUp() {
		estagiario = new Estagiario(
				"Pedro Lucas", "pedro@gmail.com", "20251208", 
				PeriodoEstagio.ESTAGIO_I, "1234");
	}

	@Test
	@Order(1)
	void deveSalvar_Estagiario_NoBancoDeDadosPeloService() {
		assertEquals(0, estagiarioRepository.count());
		
		Estagiario estagiarioSalvo = estagiarioService.salvar(new EstagiarioDto(estagiario));
		
		assertEquals("Pedro Lucas", estagiarioSalvo.getNome());
		assertEquals("pedro@gmail.com", estagiarioSalvo.getEmail());
		assertEquals("20251208", estagiarioSalvo.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiarioSalvo.getPeriodo());
		assertEquals("1234", estagiarioSalvo.getSenha());
		assertEquals(1, estagiarioRepository.count());

	}
	
	@Test
	@Order(2)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		estagiarioRepository.deleteAll();
		assistidoRepository.deleteAll();
		enderecoRepository.deleteAll();
		processoRepository.deleteAll();
	}

}
