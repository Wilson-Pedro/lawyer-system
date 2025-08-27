package com.advocacia.estacio.projections;

public interface ProcessoProjection {

	Long getId();
	
	Integer getNumeroDoProcesso();
	
	String getAssunto();
	
	String getPrazoFinal();
	
	String getResponsavel();
}
