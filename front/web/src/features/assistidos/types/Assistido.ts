export interface AssistidoRequest {
  id: number;
  nome: string;
  matricula: string;
  telefone: string;
  email: string;
  profissao: string;
  nacionalidade: string;
  naturalidade: string;
  estadoCivil: string;
  cidade: string;
  bairro: string;
  rua: string;
  numeroDaCasa: number;
  cep: string;
}
export interface AssistidoResponse {
  id: number;
  nome: string;
  matricula: string;
  telefone: string;
  email: string;
  profissao: string;
  nacionalidade: string;
  naturalidade: string;
  estadoCivil: string;
  cidade: string;
  bairro: string;
  rua: string;
  numeroDaCasa: number;
  cep: string;
}

export interface AssistidoListResponse {
  id: number;
  nome: string;
  email: string;
  usuarioStatus: string;
  registro: string;
}
