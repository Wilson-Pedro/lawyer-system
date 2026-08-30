import { api } from '../../../config/api';
import { AuthRequest } from '../types/Auth';

export const authsService = {
  logar: async (dados: AuthRequest): Promise<void> => {
    await api.post('/auth/login', dados);
  },
};
