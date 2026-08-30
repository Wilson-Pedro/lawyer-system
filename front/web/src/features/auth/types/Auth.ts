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
