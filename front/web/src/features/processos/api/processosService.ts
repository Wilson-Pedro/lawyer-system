import { api } from '../../../config/api';
import { ProcessoRequest } from '../types/ProcessosRequest';

export const processosService = {
  cadastrar: async (dados: ProcessoRequest): Promise<void> => {
    await api.post('/processos/', dados);
  },
};
