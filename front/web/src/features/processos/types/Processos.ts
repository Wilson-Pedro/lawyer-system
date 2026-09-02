export interface ProcessoRequest {
  assistidoId: number;
  numeroDoProcessoPje: string;
  assunto: string;
  vara: string;
  responsavel: string;
  advogadoId: number;
  estagiarioId: number;
  areaDoDireito: string;
  tribunal: string;
  prazo: string;
}

export interface ProcessoResponse {
  id: number;
  assistidoId: number;
  assistidoNome: string;
  numeroDoProcesso: string;
  numeroDoProcessoPje: string;
  assunto: string;
  vara: string;
  prazoFinal: string;
  responsavel: string;
  advogadoId: number;
  estagiarioId: number;
  estagiarioNome: string;
  advogadoNome: string;
  areaDoDireito: string;
  tribunal: string;
  statusDoProcesso: string;
  partesEnvolvidas: string;
  ultimaAtualizacao: string;
}
