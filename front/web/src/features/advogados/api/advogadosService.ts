import { api } from '../../../config/api';
import { AdvogadoRequest } from '../types/Advogado';

export const advogadosService = {
  cadastrar: async (dados: AdvogadoRequest): Promise<void> => {
    await api.post('/advogados/', dados);
  },
};
