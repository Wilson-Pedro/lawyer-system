package com.advocacia.estacio.modules.processos;

public enum TipoParte {
    AUTOR_REQUERENTE("Autor / Requerente"),
    REU_REQUERIDO("Réu / Requerido"),
    LITISCONSORTE_ATIVO("Litisconsorte Ativo"),
    LITISCONSORTE_PASSIVO("Litisconsorte Passivo"),
    TERCEIRO_INTERESSADO("Terceiro Interessado"),
    MINISTERIO_PUBLICO("Ministério Público");

    private final String descricao;

    TipoParte(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static String obterDescricao(TipoParte tipoParte) {
        if (tipoParte== null) {
            return null;
        }
        return tipoParte.getDescricao();
    }
}
