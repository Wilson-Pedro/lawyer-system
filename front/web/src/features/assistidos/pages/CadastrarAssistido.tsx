import React, { useState } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import { Toast, ToastContainer } from 'react-bootstrap';
import { Form } from '../../../components/Form/Form';
import { assistidosService, AssistidoRequest } from '../';
// import { zodResolver } from "@hookform/resolvers/zod";
// import { meuSchemaZod } from "../schemas/estagiariosSchemas";

// import { scrollToTop } from "./../../utils/Utils";
import { Input } from '../../../components/Form/Imput';
import { Select } from '../../../components/Form/Select';
import { Button } from '../../../components/Form/Button';
import { Container } from '../../../components/Form/Container';

import { UseFormSetError } from 'react-hook-form';
import { tratarErrosBackend } from '../../../utils/errorHelper';

export default function CadastrarAssistido() {
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
    dados: AssistidoRequest,
    setError: UseFormSetError<AssistidoRequest>,
  ) => {
    try {
      await assistidosService.cadastrar(dados);
      // Se deu certo: mostra sucesso e volta para a página anterior após 2 segundos
      setToast({
        mostrar: true,
        mensagem: 'Assistido cadastrado com sucesso!',
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
      <h2 className="mb-4 text-center">Cadastrar Assistido</h2>

      <Form<AssistidoRequest> onSubmit={handleSalvar}>
        <Input name="nome" label="Nome Completo" />
        <Input name="email" label="Email" type="email" />

        {/*  <Select
                        name="periodo"
                        label="Período do Estágio"
                        options={periodosOptions}
                    />
                  */}
        <Input name="telefone" label="Telefone" />
        <Input name="dataDeNascimento" label="Data de Nascimento" type="date" />
        <Input name="cidade" label="Cidade" />
        <Input name="bairro" label="Bairro" />
        <Input name="rua" label="Rua" />
        <Input name="numeroDaCasa" label="Número da Casa" type="number" />
        <Input name="cep" label="CEP" />
        <Input name="usuarioStatus" label="Status do Usuário" />
        <Input name="senha" label="Senha" type="password" />

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
