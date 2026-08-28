import { UseFormSetError, FieldValues } from "react-hook-form";

/**
 * Trata erros do backend. 
 * Retorna uma string (mensagem global) se for erro de regra de negócio, 
 * ou null se foram apenas erros de validação nos campos.
 */
export function tratarErrosBackend<T extends FieldValues>(
    error: any, 
    setError: UseFormSetError<T>
): string | null {
    const status = error.response?.status;
    const responseData = error.response?.data;

    // Cenário 1: Erros de Validação (Fields)
    if (status === 400 && responseData?.fields && Array.isArray(responseData.fields)) {
        responseData.fields.forEach((erro: { field: string, message: string }) => {
            setError(erro.field as any, {
                type: "server",
                message: erro.message,
            });
        });
    }

    // Cenário 2: Erros Globais (Regra de Negócio ou Servidor)
    return responseData?.message || "Ocorreu um erro inesperado. Tente novamente.";
}