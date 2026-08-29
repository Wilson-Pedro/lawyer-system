// import { z } from "zod";
// import { PeriodoEstagio } from "./types"; // O seu Enum

// export const estagiarioSchema = z.object({
//   nome: z.string().min(3, "O nome deve ter pelo menos 3 letras."),
//   email: z.string().email("Digite um e-mail válido."),
//   telefone: z.string().min(8, "O telefone deve ter no mínimo 8 dígitos."),
//   matricula: z.string().min(1, "A matrícula é obrigatória."),
//   // Valida automaticamente se a string recebida pertence ao seu Enum Java
//   periodo: z.nativeEnum(PeriodoEstagio, {
//     errorMap: () => ({ message: "Selecione um período válido." })
//   }),
//   senha: z.string().min(6, "A senha deve ter no mínimo 6 caracteres.")
// });

// // "Mágica" do Zod: Você extrai a tipagem Typescript direto do schema!
// // Você pode usar isso no lugar da interface EstagiarioRequest que criamos antes.
// export type EstagiarioFormData = z.infer<typeof estagiarioSchema>;
