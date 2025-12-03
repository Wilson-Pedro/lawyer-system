package com.advocacia.estacio.exceptions;

import com.advocacia.estacio.domain.enums.*;
import com.advocacia.estacio.services.AdvogadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EnumExceptionTest {

	// ÁREA DO DIREITO
	@Test
	void deve_lancar_excecao_EnumException_para_AreaDoDireito_por_codigo() {
		assertThrows(EnumException.class, () -> AreaDoDireito.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_AreaDoDireito_por_descricao() {
		assertThrows(EnumException.class, () -> AreaDoDireito.toEnum("error"));
	}

	// DEMANDA STATUS
	@Test
	void deve_lancar_excecao_EnumException_para_DemandaStatus_por_codigo() {
		assertThrows(EnumException.class, () -> DemandaStatus.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_DemandaStatus_por_status() {
		assertThrows(EnumException.class, () -> DemandaStatus.toEnum("error"));
	}

	// ESTADO CIVIL
	@Test
	void deve_lancar_excecao_EnumException_para_EstadoCivil_por_codigo() {
		assertThrows(EnumException.class, () -> EstadoCivil.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_EstadoCivil_por_estado() {
		assertThrows(EnumException.class, () -> EstadoCivil.toEnum("error"));
	}

	// PERIODO ESTADO
	@Test
	void deve_lancar_excecao_EnumException_para_PeriodoEstagio_por_codigo() {
		assertThrows(EnumException.class, () -> PeriodoEstagio.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_PeriodoEstagio_por_tipo() {
		assertThrows(EnumException.class, () -> PeriodoEstagio.toEnum("error"));
	}

	// RESPONDIDO POR
	@Test
	void deve_lancar_excecao_EnumException_para_RespondidoPor_por_codigo() {
		assertThrows(EnumException.class, () -> RespondidoPor.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_RespondidoPor_por_tipo() {
		assertThrows(EnumException.class, () -> RespondidoPor.toEnum("error"));
	}

	// STATUS PROCESSO
	@Test
	void deve_lancar_excecao_EnumException_para_StatusProcesso_por_codigo() {
		assertThrows(EnumException.class, () -> StatusProcesso.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_StatusProcesso_por_tipo() {
		assertThrows(EnumException.class, () -> StatusProcesso.toEnum("error"));
	}

	// TIPO DO ATOR
	@Test
	void deve_lancar_excecao_EnumException_para_TipoDoAtor_por_codigo() {
		assertThrows(EnumException.class, () -> TipoDoAtor.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_TipoDoAtor_por_tipo() {
		assertThrows(EnumException.class, () -> TipoDoAtor.toEnum("error"));
	}

	// TRIBUNAL
	@Test
	void deve_lancar_excecao_EnumException_para_Tribunal_por_codigo() {
		assertThrows(EnumException.class, () -> Tribunal.toEnum(6));
	}

	@Test
	void deve_lancar_excecao_EnumException_para_Tribunal_por_tipo() {
		assertThrows(EnumException.class, () -> Tribunal.toEnum("error"));
	}
}
