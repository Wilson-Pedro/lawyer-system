import { api } from '../../../config/api';
import { EstagiarioRequest } from '../types/EstagiarioRequest';

export const estagiariosService = {
  buscarPeriodos: async (): Promise<string[]> => {
    const response = await api.get('/estagiarios/periodos');
    return response.data;
  },

  cadastrar: async (dados: EstagiarioRequest): Promise<void> => {
    await api.post('/estagiarios', dados);
  },
};
