import { api } from '../../../config/api';
import { AuthRequest } from '../types/AuthRequest';

export const authsService = {
  autenticar: async (dados: AuthRequest): Promise<void> => {
    await api.post('/auth/', dados);
  },
};
