package com.advocacia.estacio.projections;

public interface ProcessoProjection {

	Long getId();
	
	String getNumeroDoProcesso();
	
	String getAssunto();
	
	String getPrazoFinal();
	
	String getResponsavel();
	
	Long getAdvogadoId();
	
	String getAdvogadoNome();
}
