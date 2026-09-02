import { api } from '../../../config/api';
import { DemandaRequest } from '../types/Demanda';

export const demandasService = {
  buscarPeriodos: async (): Promise<string[]> => {
    const response = await api.get('/demandas/periodos');
    return response.data;
  },

  cadastrar: async (dados: DemandaRequest): Promise<void> => {
    await api.post('/demandas', dados);
  },
};
