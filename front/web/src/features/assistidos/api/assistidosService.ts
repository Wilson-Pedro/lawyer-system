import { api } from '../../../config/api';
import { AssistidoRequest } from '../types/Assistido';

export const assistidosService = {
  cadastrar: async (dados: AssistidoRequest): Promise<void> => {
    await api.post('/assistidos/', dados);
  },
};
