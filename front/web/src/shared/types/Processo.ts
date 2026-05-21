export type Processo = {
    id: number;
    numeroDoProcesso: string;
    assunto: string;
    prazoFinal: string;
    responsavel: string;
    advogadoNome: string;
    statusDoProcesso: "Tramitando" | "Suspenso" | "Arquivado";
}