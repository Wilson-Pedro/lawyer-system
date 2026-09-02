import { Role } from '../../../config/roles';

export interface AuthRequest {
  login: string;
  password: string;
}

export interface User {
  id: string;
  nome: string;
  email: string;
  role: Role;
}

export interface AuthResponse {
  id: number;
  nome: string;
  email: string;
  tipoAtor: string;
  usuarioStatus: string;
  senha: string;
}
