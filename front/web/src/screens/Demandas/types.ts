export type DemandaResponse = {
    id: number;
    demandaId: string;
    estagiarioId: string;
    estagiarioNome: string; 
    resposta: string;
    respondidoPor: string;
    registro:string;
}

export type Demanda = {
    id: number;
    demanda: string;
    estagiarioNome: string;
    advogadoNome: string;
    professorNome: string;
    estagiarioId: string;
    professorId: string;
    advogadoId: string;
    demandaStatusAluno: string;
    demandaStatusProfessor: string;
    prazo: string;
}

export type DemandaAdvogado = {
    id: number;
    demanda: string;
    estagiarioNome: string;
    estagiarioId: string;
    demandaStatusAluno: string;
    demandaStatusProfessor: string;
    prazo: string;
    tempestividade:string;
}

// Demanda {
//     id: number;
//     demanda: string;
//     estagiarioNome: string;
//     estagiarioId: string;
//     demandaStatusAluno: string;
//     demandaStatusProfessor: string;
//     prazo: string;
//     tempestividade:string;
// }