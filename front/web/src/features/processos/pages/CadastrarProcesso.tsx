import React, { useState } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import { Toast, ToastContainer } from 'react-bootstrap';
import { Form } from '../../../components/Form/Form';
import { processosService, ProcessoRequest } from '../';
// import { zodResolver } from "@hookform/resolvers/zod";
// import { meuSchemaZod } from "../schemas/estagiariosSchemas";

// import { scrollToTop } from "./../../utils/Utils";
import { Input } from '../../../components/Form/Imput';
import { Select } from '../../../components/Form/Select';
import { Button } from '../../../components/Form/Button';
import { Container } from '../../../components/Form/Container';

import { UseFormSetError } from 'react-hook-form';
import { tratarErrosBackend } from '../../../utils/errorHelper';

export default function CadastrarProcesso() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const [toast, setToast] = useState({
    mostrar: false,
    mensagem: '',
    variante: 'success',
  });

  // Gera as opções instantaneamente a partir do Enum local sem chamar a API
  // Mais performance se os Periodos forem estáticos
  /*
    const periodosOptions = Object.values(PeriodoEstagio).map((valorEnum) => ({
        value: valorEnum,
        label: periodoEstagioLabel[valorEnum as PeriodoEstagio],
    
        }));
*/
  if (!token) return <Navigate to="/login" />;

  const handleSalvar = async (
    dados: ProcessoRequest,
    setError: UseFormSetError<ProcessoRequest>,
  ) => {
    try {
      await processosService.cadastrar(dados);
      // Se deu certo: mostra sucesso e volta para a página anterior após 2 segundos
      setToast({
        mostrar: true,
        mensagem: 'Processo cadastrado com sucesso!',
        variante: 'success',
      });
      setTimeout(() => navigate(-1), 2000);
    } catch (error: any) {
      console.error(error);

      const mensagem = tratarErrosBackend(error, setError);
      if (mensagem) {
        setToast({ mostrar: true, mensagem, variante: 'danger' });
      }

      // setToast({ mostrar: true, mensagem: "Erro ao cadastrar estagiário.", variante: "danger" });
    }
  };

  return (
    <Container>
      <h2 className="mb-4 text-center">Cadastrar Processo</h2>

      <Form<ProcessoRequest> onSubmit={handleSalvar}>
        <Input name="numeroDoProcesso" label="Número do Processo" />
        <Input name="assunto" label="Assunto" />
        <Input name="vara" label="Vara" />

        {/*  <Select
                        name="periodo"
                        label="Período do Estágio"
                        options={periodosOptions}
                    />
                  */}
        <Input name="responsavel" label="Responsável" />
        <Input name="areaDoDireito" label="Área do Direito" />
        <Input name="tribunal" label="Tribunal" />
        <Input name="prazo" label="Prazo" />

        <Button type="submit">Enviar Cadastro</Button>
      </Form>

      <ToastContainer
        position="top-end"
        className="p-3"
        style={{ zIndex: 9999, position: 'fixed' }}
      >
        <Toast
          onClose={() => setToast({ ...toast, mostrar: false })}
          show={toast.mostrar}
          bg={toast.variante}
          delay={4000}
          autohide
        >
          <Toast.Body className="text-white">{toast.mensagem}</Toast.Body>
        </Toast>
      </ToastContainer>
    </Container>
  );
}
