export interface AdvogadoRequest {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  dataDeNascimento: string;
  cidade: string;
  bairro: string;
  rua: string;
  numeroDaCasa: number;
  cep: string;
  usuarioStatus: string;
  senha: string;
}
export interface AdvogadoResponse {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  dataDeNascimento: string;
  cidade: string;
  bairro: string;
  rua: string;
  numeroDaCasa: number;
  cep: string;
  usuarioStatus: string;
  senha: string;
}

export interface AdvogadoListResponse {
  id: number;
  nome: string;
  email: string;
  usuarioStatus: string;
  registro: string;
}
