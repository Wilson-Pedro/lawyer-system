import { api } from "../../../config/api";
import { AtorRequest } from "../types/AtorRequest"; 

export const atoresService = {
  cadastrar: async (dados: AtorRequest): Promise<void> => {
    await api.post("/atores/", dados);
  },
};