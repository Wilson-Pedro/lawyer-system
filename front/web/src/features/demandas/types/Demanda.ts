export interface DemandaRequest {
  demanda: string;
  estagiarioNome: string;
  professorNome: string;
  advogadoNome: string;
  estagiarioId: number;
  professorId: number;
  advogadoId: number;
  demandaStatusAluno: string;
  demandaStatusProfessor: string;
  demandaStatusAdvogado: string;
  prazoDocumentos: string;
  prazo: string;
  diasPrazo: number;
  tempestividade: string;
}

export interface DemandaResponse {
  id: number;
  demanda: string;
  estagiarioNome: string;
  professorNome: string;
  advogadoNome: string;
  estagiarioId: number;
  professorId: number;
  advogadoId: number;
  demandaStatusAluno: string;
  demandaStatusProfessor: string;
  demandaStatusAdvogado: string;
  prazoDocumentos: string;
  prazo: string;
  diasPrazo: number;
  tempestividade: string;
}

export interface DemandaListResponse {
  id: number;
  demanda: string;
  estagiarioNome: string;
  professorNome: string;
  advogadoNome: string;
  estagiarioId: number;
  professorId: number;
  advogadoId: number;
  demandaStatusAluno: string;
  demandaStatusProfessor: string;
  demandaStatusAdvogado: string;
  prazoDocumentos: string;
  prazo: string;
  diasPrazo: number;
  tempestividade: string;
}
